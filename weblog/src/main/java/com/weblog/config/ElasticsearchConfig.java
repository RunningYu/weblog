package com.weblog.config;

import io.swagger.annotations.ApiModel;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "ES搜索引擎的配置 ")
@Configuration
public class ElasticsearchConfig {

    @Value("${ES.url}")
    private String url;

    @Bean
    public RestHighLevelClient client(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create(url)
        ));
    }

}
