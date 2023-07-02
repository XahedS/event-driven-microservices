package com.microservices.demo.elastic.query.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticQueryServiceResponseModelV2 extends RepresentationModel<ElasticQueryServiceResponseModelV2> {
    //The added extension will change the Response Object to a Rest Representation model. This will be helpful in
    //setting hateoas related links on the response object.
    private Long id; //changed to make a breaking change! Moreover removed createdAt field for the breaking chnage as well.
    private String text;
    private Long userId;
    private String textV2;
}
