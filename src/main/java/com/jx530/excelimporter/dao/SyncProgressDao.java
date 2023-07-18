package com.jx530.excelimporter.dao;

import com.jx530.excelimporter.model.SyncProgress;
import com.jx530.excelimporter.model.SyncType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SyncProgressDao extends JpaRepository<SyncProgress, Long> {
    List<SyncProgress> findByFinished(boolean finished);

    List<SyncProgress> findByTypeIn(Collection<SyncType> types);

}
