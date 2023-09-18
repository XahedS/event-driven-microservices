package com.microservices.demo.reactive.elastic.query.service.business;

import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ElasticQueryService {

    Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);
}
