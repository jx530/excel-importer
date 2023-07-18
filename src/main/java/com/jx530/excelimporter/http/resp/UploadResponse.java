package com.jx530.excelimporter.http.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UploadResponse {

    private String returnCode;
    private String returnMessage;
    private ReturnDataDTO returnData;
    private Object returnCount;

    @NoArgsConstructor
    @Data
    public static class ReturnDataDTO {
        private String time;
    }
}
