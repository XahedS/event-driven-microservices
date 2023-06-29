package com.microservices.demo.elastic.query.service.business.impl;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.client.service.ElasticQueryClient;
import com.microservices.demo.elastic.query.service.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.model.assembler.ElasticQueryServiceResponseModelAssembler;
import com.microservices.demo.elastic.query.service.transformer.ElasticToResponseModelTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TwitterElasticQueryService implements ElasticQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);
//    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;
    private final ElasticQueryServiceResponseModelAssembler elasticToResponseModelTransformer;
    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(ElasticToResponseModelTransformer elasticToResponseModelTransformer,
                                      ElasticQueryServiceResponseModelAssembler elasticToResponseModelTransformer1,
                                      ElasticQueryClient<TwitterIndexModel> elasticQueryClient) {
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer1;
//        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
//        this.elasticQueryClient = elasticQueryClient;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        LOG.info("Querying elastic search by id {}", id);
//        return elasticToResponseModelTransformer.getResponseModel(elasticQueryClient.getIndexModelById(id));
        return elasticToResponseModelTransformer.toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        LOG.info("Querying elastic search by text {}", text);
//        return elasticToResponseModelTransformer.getResponseModels(elasticQueryClient.
//                getIndexModelByText(text));
        return elasticToResponseModelTransformer.toModels(elasticQueryClient.
                getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        LOG.info("Querying all documents in elastic search");
//        return elasticToResponseModelTransformer.getResponseModels(elasticQueryClient.getAllIndexModels());
        return elasticToResponseModelTransformer.toModels(elasticQueryClient.getAllIndexModels());
    }
}
