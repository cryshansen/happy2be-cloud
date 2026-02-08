package com.artogco.happy2be.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class PatternService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public PatternService(@Value("${openai.api.key}") String openAiApiKey, WebClient.Builder webClientBuilder) {
        this.openAiApiKey = openAiApiKey;
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.openAiApiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ PatternService initialized with OpenAI API Key");
    }

    // -----------------------
    // 🪷 1. Get Mood Patterns
    // -----------------------
    public Map<String, Object> getMoodPatterns(String feeling) {
        String prompt = """
            You are an emotional well-being coach that helps users identify thought patterns behind their moods.

            User feeling: "%s"

            Respond in compact JSON with:
            {
              "feeling": "%s",
              "patterns": [
                "underlying thought 1",
                "underlying thought 2"
              ],
              "practices": [
                {
                  "title": "practice name",
                  "description": "short soothing description"
                }
              ]
            }
            Keep your tone gentle and supportive.
            """.formatted(feeling, feeling);

        String response = callOpenAI(prompt);
        Map<String, Object> result = new HashMap<>();
        result.put("feeling", feeling);
        result.put("patterns", response);
        return result;
    }

    // -----------------------
    // 🌿 2. Expand Feelings
    // -----------------------
    public Map<String, Object> expandFeeling(String query) {
        String prompt = """
            The user entered an emotional word: "%s"

            List 5 related feelings or emotional states, each as a single word or short phrase.

            Return JSON:
            {
              "base": "%s",
              "related": ["...", "...", "...", "...", "..."]
            }
            """.formatted(query, query);

        String response = callOpenAI(prompt);
        Map<String, Object> result = new HashMap<>();
        result.put("base", query);
        result.put("related", response);
        return result;
    }

    // -----------------------
    // 🌸 3. Generate Affirmations
    // -----------------------
    public Map<String, Object> getAffirmations(String feeling) {
        String prompt = """
            The user feels "%s".
            Generate 3 gentle affirmations to help them find balance and self-acceptance.

            Return JSON:
            {
              "feeling": "%s",
              "affirmations": ["...", "...", "..."]
            }
            """.formatted(feeling, feeling);

        String response = callOpenAI(prompt);
        Map<String, Object> result = new HashMap<>();
        result.put("feeling", feeling);
        result.put("affirmations", response);
        return result;
    }

    // -----------------------
    // 🌊 4. Intensity Reflection
    // -----------------------
    public Map<String, Object> getIntensityReflection(String feeling, int intensity) {
        String prompt = """
            The user reported feeling "%s" at intensity level %d (1-10).

            Provide a one-sentence reflection that acknowledges their feeling and encourages calm awareness.
            Do not give advice — offer gentle presence.

            Return JSON:
            {
              "message": "short empathetic reflection"
            }
            """.formatted(feeling, intensity);

        String response = callOpenAI(prompt);
        Map<String, Object> result = new HashMap<>();
        result.put("feeling", feeling);
        result.put("intensity", intensity);
        result.put("reflection", response);
        return result;
    }

    // -----------------------
    // 🌼 5. Shared OpenAI Helper
    // -----------------------
    private String callOpenAI(String prompt) {
        try {
            Map<String, Object> response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(Map.of(
                            "model", "gpt-3.5-turbo",
                            "messages", List.of(Map.of("role", "user", "content", prompt)),
                            "max_tokens", 300
                    ))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5))
                            .filter(throwable -> throwable instanceof WebClientResponseException.TooManyRequests))
                    .block();

            if (response == null) return "No response from OpenAI.";

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                return (String) message.get("content");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error contacting OpenAI: " + e.getMessage();
        }

        return "No help available right now.";
    }
}
