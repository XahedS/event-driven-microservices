package com.microservices.demo.elastic.query.service.business;

import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;

import java.util.List;

public interface ElasticQueryService {
    public ElasticQueryServiceResponseModel getDocumentById(String id);
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text);
    public List<ElasticQueryServiceResponseModel> getAllDocuments();
}
