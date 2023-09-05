package com.mk.openai.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@Slf4j
public class ConfigMap {

    @Value("${cro.token-cache-time}")
    private Integer tokenCacheTime;

    @Value("${cro.upload-file-path:/data/cro}")
    private String uploadFilePath;

    @Value("${cro.ai-url}")
    private String aiUrl;
}
