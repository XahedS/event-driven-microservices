package com.microservices.demo.reactive.elastic.query.service.repository;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ElasticQueryRepository extends ReactiveCrudRepository<TwitterIndexModel, String> {
//    Flux is simply a stream which can emit 0 to n elements
//    Mono & Flux are reactive elements, where mono emits 0 to 1 element
    Flux<TwitterIndexModel> findByText(String text);
}
