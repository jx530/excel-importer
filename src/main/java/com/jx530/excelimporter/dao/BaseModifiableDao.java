package com.jx530.excelimporter.dao;

import com.jx530.excelimporter.model.BaseModel;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BaseModifiableDao<T extends BaseModel> {

    @Query("select max(__modified) from #{#entityName}")
    LocalDateTime findMaxModified();

    List<T> findTop100ByModifiedBetween(LocalDateTime step, LocalDateTime now);
}
