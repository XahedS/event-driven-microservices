package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-web-client")
public class ElasticQueryWebClientConfigData {
    private WebClient webClient;
    private Query queryByText;
    @Data
    @Component
    public static class WebClient {
        private Integer connectTimeoutMs;
        private Integer readTimeoutMs;
        private Integer writeTimeoutMs;
        private Integer maxInMemorySize;
        private String contentType;
        private String acceptType;
        private String baseUrl;
//        Adding below fields for load balancer
        private String serviceId;
        private List<Instance> instances;
    }
    @Data
    public static class Query {
        private String method;
        private String accept;
        private String uri;
    }
//Added below inner class to fetch instances of elastic-query-service
    @Data
    public static class Instance {
        private String id;
        private String host;
        private Integer port;
    }
}
