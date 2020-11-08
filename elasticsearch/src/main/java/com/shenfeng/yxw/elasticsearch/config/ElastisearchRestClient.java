package com.shenfeng.yxw.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElastisearchRestClient {
    @Value("${elastisearch.ip1}")
    String addressIp1;

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient() {
        String[] address = addressIp1.split(":");
        String ip = address[0];
        int port = Integer.valueOf(address[1]);
        HttpHost post = new HttpHost(ip, port, "http");
        return new RestHighLevelClient(RestClient.builder(new HttpHost[]{post}));
    }
}
