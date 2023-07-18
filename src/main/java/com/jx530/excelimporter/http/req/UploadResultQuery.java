package com.jx530.excelimporter.http.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UploadResultQuery {
    private String startTime;
    private String endTime;
    private String page;
    private String limit;
    private String tableName;
}
