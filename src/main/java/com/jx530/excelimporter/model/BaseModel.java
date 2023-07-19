package com.jx530.excelimporter.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    @ExcelIgnore
    private Long id;

    @Version
    @JsonIgnore
    @ExcelIgnore
    private Integer __version;

    @CreatedDate
    @JsonIgnore
    @ExcelIgnore
    private LocalDateTime __created;

    @LastModifiedDate
    @JsonIgnore
    @ExcelIgnore
    private LocalDateTime __modified;

    @JsonProperty("id")
    public String getIdString() {
        return Long.toString(id);
    }

}
