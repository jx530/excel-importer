package com.jx530.excelimporter.http.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
    private String clientId;
    private String scope;
    private String openid;
    private String clientSecret;
    private String appSecret;
}
