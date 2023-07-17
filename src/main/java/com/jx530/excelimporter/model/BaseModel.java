package com.jx530.excelimporter.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

@Data
public class BaseModel {

    @Id
    @ExcelIgnore
    private String id;

    @Version
    @ExcelIgnore
    private Integer version;

    @CreatedDate
    @ExcelIgnore
    private LocalDateTime __created;

    @LastModifiedDate
    @ExcelIgnore
    private LocalDateTime __modified;

}
