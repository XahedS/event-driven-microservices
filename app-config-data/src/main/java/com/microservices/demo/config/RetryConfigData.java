package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigData {
    private long  initialIntervalMs;
    private long    maxIntervalMs;
    private Integer multiplier;  //required for exponential backoff policy
    private Integer maxAttempts; //required for a single retry policy
    private long    sleepTimeMs;
}
