package com.jx530.excelimporter.controller;

import com.jx530.excelimporter.dao.SyncProgressDao;
import com.jx530.excelimporter.model.SyncProgress;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SyncProgressController {

    SyncProgressDao syncProgressDao;

    @GetMapping
    public Page<SyncProgress> list(Pageable pageable) {
        return syncProgressDao.findAll(pageable);
    }

    @PostMapping
    public SyncProgress saveOrUpdate(SyncProgress progress) {
        return syncProgressDao.save(progress);
    }

}
