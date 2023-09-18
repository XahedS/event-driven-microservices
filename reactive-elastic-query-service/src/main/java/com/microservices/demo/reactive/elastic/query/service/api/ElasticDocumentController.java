package com.microservices.demo.reactive.elastic.query.service.api;

import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.reactive.elastic.query.service.business.ElasticQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);
    private final ElasticQueryService elasticQueryService;

    public ElasticDocumentController(ElasticQueryService elasticQueryService) {
        this.elasticQueryService = elasticQueryService;
    }
    @PostMapping(value = "/get-doc-by-text",
    produces = MediaType.TEXT_EVENT_STREAM_VALUE, //Make this endpoint an event stream endpoint and returns the response by chunks to the client.
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(
             @RequestBody @Valid ElasticQueryServiceRequestModel requestModel)
    {
        Flux<ElasticQueryServiceResponseModel> response =
                elasticQueryService.getDocumentsByText(requestModel.getText());
        response = response.log();
//        The above method will observe all  reactive stream signals and trace them using logger support
        LOG.info("Returning from query  reactive Service for text {}!", requestModel.getText());
        return response;
    }
}
