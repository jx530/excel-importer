package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.BaseModifiableDao;
import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.dto.SSEResponse;
import com.jx530.excelimporter.dto.SyncResponse;
import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncType;
import com.jx530.excelimporter.service.SyncService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

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

    @SneakyThrows
    @GetMapping("/{id}")
    public SseEmitter updateFinish(@PathVariable Long id) {
        SyncProgress syncItem = syncProgressDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "不存在"));
        Class<? extends BaseModifiableDao<?>> daoClass = syncItem.getType().getDaoClass();
        BaseModifiableDao<?> dao = beanFactory.getBean(daoClass);
        LocalDateTime latest = dao.findMaxModified();

        syncItem.setFinished(false);
        SyncProgress.Progress p = syncItem.getProgress();
        p.setLatest(latest);
        syncItem.setProgress(p);

        syncItem = syncProgressDao.save(syncItem);

        SseEmitter emitter = new SseEmitter();

        SyncProgress finalSyncItem = syncItem;
        emitter.send(event().data(
                SSEResponse.builder().type("queue").data(new SyncResponse(syncItem, null)).build(),
                MediaType.APPLICATION_JSON));
        sseMvcExecutor.execute(() -> invokeSync(emitter, finalSyncItem));
        return emitter;
    }

    @SneakyThrows
    private void invokeSync(SseEmitter emitter, SyncProgress finalSyncItem) {
        try {
            syncService.runSyncTask(finalSyncItem, (syncResp) -> {
                try {
                    emitter.send(event().data(
                            SSEResponse.builder().type("progress").data(syncResp).build(),
                            MediaType.APPLICATION_JSON));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            });
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
        emitter.send(event().data(SSEResponse.builder().type("finish").data("数据同步完成").build(),
                MediaType.APPLICATION_JSON));
    }

}
