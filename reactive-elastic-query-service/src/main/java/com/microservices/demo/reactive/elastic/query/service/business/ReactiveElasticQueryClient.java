package com.microservices.demo.reactive.elastic.query.service.business;

import com.microservices.demo.elastic.model.index.IndexModel;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
public interface ReactiveElasticQueryClient<T extends IndexModel> {

    Flux<TwitterIndexModel> getIndexModelByText(String text);
}
