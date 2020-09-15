package com.realwinner.governmentcoronaapidaemon.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomHttpClient {

    @Bean
    public HttpClient getHttpClient() {
        log.info("createMinimal HttpCleints");
        return HttpClients.createMinimal();
    }

}
