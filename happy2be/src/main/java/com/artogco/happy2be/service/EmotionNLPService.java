package com.artogco.happy2be.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



/**
 * 
 * this app pulls the same from python to rate different text strings for positive/negative value if closer to 25% likely more neutral
 */

@Service
public class EmotionNLPService {
	@Value("${api.python.url}")
	private String pythonApiUrl;
    private final NLPService nlpService;
    
    public EmotionNLPService(NLPService nlpService) {
        this.nlpService = nlpService;
    }

    public String analyzeNLPSentiment(String text, String tone) {
        try {
            URL url = new URL(pythonApiUrl+"/analyze");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = "{\"text\": \"" + text + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                    String jsonResponse = scanner.useDelimiter("\\A").next();
                    if (jsonResponse.contains("\"label\":\"NEGATIVE\"")) {
                        return generatePositiveMessage(text, tone);
                    }
                    return jsonResponse;
                }
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Could not connect to Flask API.";
        }
    }

    private String generatePositiveMessage(String text, String tone) {
        //List<String> keywords = nlpService.extractKeywords(text);//this sorts words as nouns adj and returns thke keywwords.
        Mono<String> response = nlpService.getNLPHappy(tone, tone);
        Map<String, Map<String, String[]>> responseMap = new HashMap<>();
        
        responseMap.put("money", Map.of(
            "friendly", new String[]{
                "Don't stress, money troubles are temporary! You're gonna make it! 💪",
                "Even billionaires struggle sometimes. Keep going, you got this! 💸"
            },
            "professional", new String[]{
                "Financial struggles are tough, but resilience is key. Better days ahead! 🌟",
                "Stay strategic, keep planning, and things will improve financially. 📈"
            },
            "humorous", new String[]{
                "If being broke was an Olympic sport, we'd all have gold medals! 🤣",
                "Money talks... but mine just waves and disappears! 💸😂"
            }
        ));

        responseMap.put("depressed", Map.of(
            "friendly", new String[]{
                "You're not alone. Take small steps, and reach out to someone who cares. ❤️",
                "You're stronger than you think. Keep going, you matter! 🌍"
            },
            "professional", new String[]{
                "Mental health is important. Consider reaching out to a professional or loved one. 💙",
                "You are valued. Healing takes time, but brighter days are ahead. 🌞"
            },
            "humorous", new String[]{
                "Feeling down? Just remember... pizza exists. 🍕😄",
                "Even Batman has bad days. But he still saves Gotham! 🦇💪"
            }
        ));

        /*for (String keyword : keywords) {
            if (responseMap.containsKey(keyword)) {
                Map<String, String[]> toneMap = responseMap.get(keyword);
                String[] messages = toneMap.getOrDefault(tone, toneMap.get("friendly"));
                return messages[new Random().nextInt(messages.length)];
            }
        }*/

        return "Stay strong! Every challenge is a step towards a better you. Keep going! 💪";
    }

}
