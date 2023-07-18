package com.jx530.excelimporter.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
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
