package com.jx530.excelimporter.http.req;

import cn.hutool.crypto.digest.SM3;
import com.jx530.excelimporter.http.resp.LoginResponse;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.util.Map;

@Data
public class UploadHeaders {

    private static final SM3 sm3 = new SM3();

    private String Authorization;
    private String ContentType = "application/json";
    private String app_key;
    private String timestamps;
    private String random;
    private String sign;

    public Map<String, String> asMap() {
        return Map.of(
                "Authorization", Authorization,
                "Content-Type", ContentType,
                "app_key", app_key,
                "timestamps", timestamps,
                "random", random,
                "sign", sign
        );
    }

    public UploadHeaders(LoginResponse login, String json) {
        this.Authorization = login.getAccessToken();
        this.app_key = login.getAppSecret();
        this.timestamps = Long.toString(System.currentTimeMillis());
        this.random = RandomStringUtils.randomAlphanumeric(4);
        this.sign = ByteUtils.toHexString(sm3.digest(json + timestamps + random));
    }

}
