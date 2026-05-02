package com.protim.demo.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService implements ChatbotService {

    private final ChatClient client;

    @Override
    public String getAnswer(String question) {
        log.info("Request to model: {}", question);

        try {
            String response = client.prompt(question)
                    .call()
                    .content();
            log.info("Response from model: {}", response);

            return response;
        } catch (RuntimeException e) {
            log.error("Exception from client", e);
            return e.getMessage();
        }
    }
}
