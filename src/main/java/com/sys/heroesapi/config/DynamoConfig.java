package com.sys.heroesapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


import static com.sys.heroesapi.constants.HeroesConstat.*;


@Configuration
@EnableDynamoDBRepositories
public class DynamoConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;
	
	@Value("${aws_access_key_id}")
	private String amazonAWSAccessKey;
	
	@Value("${aws_secret_access_key}")
	private String AmazonAWSecretKey;
	
	
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		/*
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
			
			if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
				amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
			}
			*/
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT_DYNAMO,REGION_DYNAMO))
				
				.build();
		
			return amazonDynamoDB;
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey,AmazonAWSecretKey);
	}

}
