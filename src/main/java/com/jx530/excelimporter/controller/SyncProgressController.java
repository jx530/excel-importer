package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.BaseModifiableDao;
import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncType;
import com.jx530.excelimporter.service.SyncService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/sync")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SyncProgressController {

    SyncProgressDao syncProgressDao;
    SyncService syncService;
    BeanFactory beanFactory;
    ExecutorService sseMvcExecutor;

    @PostConstruct
    public void init() {
        Map<SyncType, SyncProgress> typeMap = syncProgressDao.findByTypeIn(Arrays.asList(SyncType.values()))
                .stream().collect(Collectors.toMap(SyncProgress::getType, Function.identity()));
        for (SyncType value : SyncType.values()) {
            SyncProgress progress = typeMap.get(value);
            if (progress == null) {
                SyncProgress sync = new SyncProgress(value, "0/0", true);
                sync.setProgress(SyncProgress.Progress.builder().current(now()).latest(now()).build());
                syncProgressDao.save(sync);
            }
        }
    }

    @GetMapping
    public Page<SyncProgress> list(Pageable pageable) {
        return syncProgressDao.findAll(pageable);
    }

    @PostMapping("/{id}")
    public SseEmitter updateFinish(@PathVariable Long id, boolean finished) {
        SyncProgress syncItem = syncProgressDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "不存在"));
        Class<? extends BaseModifiableDao<?>> daoClass = syncItem.getType().getDaoClass();
        BaseModifiableDao<?> dao = beanFactory.getBean(daoClass);
        LocalDateTime latest = dao.findMaxModified();

        syncItem.setFinished(finished);
        SyncProgress.Progress p = syncItem.getProgress();
        p.setLatest(latest);
        syncItem.setProgress(p);

        syncItem = syncProgressDao.save(syncItem);

        SseEmitter emitter = new SseEmitter();

        SyncProgress finalSyncItem = syncItem;
        sseMvcExecutor.execute(() -> {
            try {
                syncService.runSyncTask(finalSyncItem, (syncResp) -> {
                    try {
                        emitter.send(SseEmitter.event().name("progress").data(syncResp).build());
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                });
                emitter.send(SseEmitter.event().name("success").data("success").build());
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

}
