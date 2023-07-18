package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncType;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sync")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SyncProgressController {

    SyncProgressDao syncProgressDao;

    @PostConstruct
    public void init() {
        Map<SyncType, SyncProgress> typeMap = syncProgressDao.findByTypeIn(Arrays.asList(SyncType.values()))
                .stream().collect(Collectors.toMap(SyncProgress::getType, Function.identity()));
        for (SyncType value : SyncType.values()) {
            SyncProgress progress = typeMap.get(value);
            if (progress == null) {
                syncProgressDao.save(new SyncProgress(value, "0/0", true));
            }
        }
    }

    @GetMapping
    public Page<SyncProgress> list(Pageable pageable) {
        return syncProgressDao.findAll(pageable);
    }

    @PostMapping
    public SyncProgress saveOrUpdate(SyncProgress progress) {
        return syncProgressDao.save(progress);
    }

}
