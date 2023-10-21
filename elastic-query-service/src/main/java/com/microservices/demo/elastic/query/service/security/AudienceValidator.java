package com.microservices.demo.elastic.query.service.security;

import com.microservices.demo.config.ElasticQueryServiceConfigData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
//Validates whether the audience defined in the JWT is correct or not
@Qualifier("elastic-query-service-audience-validator")
@Component
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    public AudienceValidator(ElasticQueryServiceConfigData elasticQueryServiceConfigData) {
        this.elasticQueryServiceConfigData = elasticQueryServiceConfigData;
    }
//If we have correct audience defined in the JWT we will return success otherwise failure with OAuth2Error object.
    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        if(token.getAudience().contains(elasticQueryServiceConfigData.getCustomAudience()))
        {
            return OAuth2TokenValidatorResult.success();
        }
        else {
            OAuth2Error audienceError = new OAuth2Error("invalid_token",
                    "The required audience "+ elasticQueryServiceConfigData.getCustomAudience() +
                    "is missing", null);
            return OAuth2TokenValidatorResult.failure(audienceError);
        }

    }
}
