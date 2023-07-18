package com.jx530.excelimporter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("upload")
public class UploadProps {
    private String clientId;
    private String username;
    private String password;
    private String appKey;
}
