package com.jx530.excelimporter.dao;

import com.jx530.excelimporter.model.ODS_ZZXQJCSJ;
import com.jx530.excelimporter.model.ODS_ZZXXGKJCSJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ODS_ZZXXGKJCSJDao extends JpaRepository<ODS_ZZXXGKJCSJ, Long>,
        BaseModifiableDao<ODS_ZZXQJCSJ> {
}
