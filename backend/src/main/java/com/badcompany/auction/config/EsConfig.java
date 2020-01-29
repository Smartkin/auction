package com.badcompany.auction.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@Configuration
public class EsConfig {
//    @Value("${elasticsearch.host}")
//    private String EsHost;
//
//    @Value("${elasticsearch.port}")
//    private int EsPort;
//
//    @Value("${elasticsearch.clustername}")
//    private String EsClusterName;
//
////    @Bean
////    public RestHighLevelClient client() throws Exception {
////        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
////                .connectedTo("localhost:9200", "localhost:9201")
////                .build();
////        return RestClients.create(clientConfiguration).rest();
////    }
//
//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200", "localhost:9201")
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
}
