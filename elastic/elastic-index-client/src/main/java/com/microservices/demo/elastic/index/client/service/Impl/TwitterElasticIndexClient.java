package com.microservices.demo.elastic.index.client.service.Impl;

import com.microservices.demo.config.ElasticConfigData;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.index.client.util.ElasticIndexUtil;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterElasticIndexClient implements ElasticIndexClient {

    private static  final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;
    //ElasticSearchOperations class will be used to interact with Elastic Search by sending IndexQueries.
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    public TwitterElasticIndexClient(ElasticConfigData elasticConfigData,
                                     ElasticsearchOperations elasticsearchOperations,
                                     ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil) {
        this.elasticConfigData = elasticConfigData;
        System.err.println("this.elasticConfigData  :-----------------------------" + this.elasticConfigData.getIndexName());
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticIndexUtil = elasticIndexUtil;
    }
//save method iwll convert the list of documents to list of IndexQueries
    @Override
    public List<String> save(List documents) {
        List<IndexQuery>   indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<IndexedObjectInformation> documentIds = elasticsearchOperations.bulkIndex(
          indexQueries,
          IndexCoordinates.of(elasticConfigData.getIndexName())
        );
        LOG.info("Documents indexed successfully with type: {} and ids: {}",
                TwitterIndexModel.class.getName(),
                documentIds.stream().map(IndexedObjectInformation::getId).
                        collect(Collectors.toList()));

        return documentIds.stream().map(IndexedObjectInformation::getId).collect(Collectors.toList());
//        return null;
    }
}
