package com.jx530.excelimporter.http.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadEncryptRequest {
    private String cipherText;
}
