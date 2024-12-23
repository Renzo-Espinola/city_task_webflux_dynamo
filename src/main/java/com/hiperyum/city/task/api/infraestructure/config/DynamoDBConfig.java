package com.hiperyum.city.task.api.infraestructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Objects;

@Configuration
public class DynamoDBConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBConfig.class);

    @Value("${aws.dynamodb.endpoint-override}")
    private String dynamoDBEndpoint;

    @Value("${aws.region:us-east-1}")
    private String region;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(){
        LOGGER.info("AWS Region: {}", this.region);
        LOGGER.info("DynamoDB endpoint: {}", this.dynamoDBEndpoint);
        if(Objects.nonNull(this.dynamoDBEndpoint) && !dynamoDBEndpoint.isBlank()){
            return DynamoDbAsyncClient
                    .builder()
                    .endpointOverride(URI.create(dynamoDBEndpoint))
                    .region(Region.of(region))
                    .build();
        }
        return DynamoDbAsyncClient.builder().build();
    }
}
