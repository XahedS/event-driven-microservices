//package com.microservices.demo.elastic.query.web.client.config;
//
//import com.microservices.demo.config.ElasticQueryWebClientConfigData;
//import org.springframework.cloud.client.DefaultServiceInstance;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.Request;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//@Primary //We want to use this class and not the implementations of ServiceInstanceListSupplier available with Spring classes
////This annotation gives implementation priority to be loaded by Spring.
//public class ElasticQueryServiceInstanceListSupplierConfig implements ServiceInstanceListSupplier {
//    private final ElasticQueryWebClientConfigData.WebClient webClient;
//
//    public ElasticQueryServiceInstanceListSupplierConfig(ElasticQueryWebClientConfigData.WebClient webClient) {
//        this.webClient = webClient;
//    }
//
//    @Override
//    public String getServiceId() {
//        return webClient.getServiceId();
//    }
////The below method will return the instances we configured in the "config-client-elastic_query_web.yml"
//    //Flux is a reactive stream that can return 0 to n elements.
//    @Override
//    public Flux<List<ServiceInstance>> get() {
//        return Flux.just(
//                webClient.getInstances().stream()
//                        .map(instance ->
//                                new DefaultServiceInstance(
//                                        instance.getId(),
//                                        getServiceId(),
//                                        instance.getHost(),
//                                        instance.getPort(),
//                                        false
//                                )).collect(Collectors.toList()));
//    }
//
//
//}
