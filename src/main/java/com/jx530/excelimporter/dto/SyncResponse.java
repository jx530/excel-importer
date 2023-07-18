package com.jx530.excelimporter.dto;

import com.jx530.excelimporter.http.resp.UploadResponse;
import com.jx530.excelimporter.model.SyncProgress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncResponse {

    SyncProgress progress;
    UploadResponse uploadResponse;

}
