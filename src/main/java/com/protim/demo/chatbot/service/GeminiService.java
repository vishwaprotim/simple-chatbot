package com.protim.demo.chatbot.service;

import com.google.genai.errors.ClientException;
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
    public String getAnswer(String question){
        log.info("Request to model: {}", question);

        try{
            String response = client.prompt().user(question).call().content();
            log.info("Response from model: {}", response);

            return response;
        } catch (RuntimeException e){
            log.error("Exception from client", e);
            return e.getMessage();
        } finally {
            log.error("From finally!");
        }
    }
}

/**
 * Quota exceeded for metric: generativelanguage.googleapis.com/generate_content_free_tier_input_token_count, limit: 0, model: gemini-2.0-flash
 * The limit: 0 error appears because your Google Cloud project does not have Free Tier quotas activated.
 * Since March 2024, Google requires linking a billing account even for Gemini Free Tier.
 * If billing is not enabled, the free quotas for:
 *
 * generate_content_free_tier_requests
 * input_token_count
 * output_token_count
 * are all 0, which causes the 429 ResourceExhausted error even on a new project.
 *
 * After you link a billing account, the free-tier quotas (50 requests/day, token limits, etc.) become available and the API will work.
 *
 *
 * ClientException: 404 . This model models/gemini-2.0-flash-001 is no longer available to new users. Please update your code to use a newer model for the latest features and improvements.
 *
 *
 */
