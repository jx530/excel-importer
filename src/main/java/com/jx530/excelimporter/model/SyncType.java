package com.jx530.excelimporter.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.jx530.excelimporter.dao.BaseModifiableDao;
import com.jx530.excelimporter.dao.ODS_ZZXQJCSJDao;
import com.jx530.excelimporter.dao.ODS_ZZXXGKJCSJDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public enum SyncType {

    ODS_ZZXXGKJCSJ("学校基本数据子类表", "表1：学校基本数据子类表_导入模板.xlsx", ODS_ZZXQJCSJDao.class),
    ODS_ZZXQJCSJ("校区基本数据子类表", "表2：校区基本数据子类表_导入模板.xlsx", ODS_ZZXXGKJCSJDao.class),
    ;


    private String title;
    private String template;
    @Getter
    private Class<? extends BaseModifiableDao<?>> daoClass;

    @JsonValue
    public Map<String, String> jsonMapping() {
        return Map.of("name", name(), "title", title, "template", template);
    }
}
