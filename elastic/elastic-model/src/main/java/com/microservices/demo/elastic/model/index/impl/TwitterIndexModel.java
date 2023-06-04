package com.microservices.demo.elastic.model.index.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.demo.elastic.model.index.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.json.Json;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
//This class converts TwittertoKafka object into an IndexModel object which will be later converted to a document
//in elastic-index-client module.

@Builder
@Data
@Document(indexName = "#{elasticConfigData.indexName") //indicates that this class is a candidate for mapping
// to elastic search. Moreover, we used spring expression language to fetch the index name from the config file.
public class TwitterIndexModel implements IndexModel {
    @JsonProperty
    private String id;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String text;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    //Need this annotation to convert the createdAt variable from  LocalDateTime to the elastic search date
    //during indexing operation
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    //The annotation formats the field when converting
    //object to json by using the pattern specified.
//    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonProperty
    private ZonedDateTime createdAt;    //Chnaged LocalDateTime to ZonedDateTime here,
//    private LocalDateTime createdAt;
//Removed below part because it iwll be automatically added by Lombok dependency i.e. the setters & getters
//    @Override
//    public String getId() {
//        return id;
//    }
}
