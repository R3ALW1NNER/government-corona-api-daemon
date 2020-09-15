package com.realwinner.governmentcoronaapidaemon.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TelegramMessage {
    @Autowired
    private CustomHttpClient customHttpClient;

    @Value("${telegram.sendMessageUrl}")
    private String sendMessageUrl;

    @Value("${telegram.chatId}")
    private String telegramChatId;

    public void send(String message) {
        HttpClient httpClient = customHttpClient.getHttpClient();

        log.info("telegram.chatId = {}", telegramChatId);
        log.info("telegram URL = {}", sendMessageUrl);

        try {
            URI uri = new URIBuilder(new URI(sendMessageUrl))
                    .addParameter("chat_id", telegramChatId)
                    .addParameter("text", URLDecoder.decode(message, StandardCharsets.UTF_8.name()))
                    .build();

            httpClient.execute(new HttpGet(uri));

            log.info("message send :: {}", uri.toString());

        } catch (Exception e) {
            log.error("Telegram message send fail :: ", e);
        }
    }
}
