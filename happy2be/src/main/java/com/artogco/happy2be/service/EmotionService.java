package com.artogco.happy2be.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.artogco.happy2be.dto.*;
import com.artogco.happy2be.domain.*;

/**
 * 
 * pulls from Python happyfacesFlask.py to get rating of sentiment so can provide different responses based on positive/negative text ranking
 * working copy
 */
@Service
public class EmotionService {
	@Value("${api.python.url}")
	private String pythonApiUrl;

    public String analyzeSentiment(String text) {
        try {
            // Flask API URL (check correct port!)
            URL url = new URL(pythonApiUrl+"/analyze");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configure HTTP request
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON Request Payload
            String jsonInput = "{\"text\": \"" + text + "\"}";

            // Write JSON to request body
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            // Read Response v1
/*          int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // HTTP 200 OK
                try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                    return scanner.useDelimiter("\\A").next();
                }
            } else {
                return "Error: " + responseCode;
            }
*/
            //v2
            // Read Response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                    String jsonResponse = scanner.useDelimiter("\\A").next();
                    
                    // Extract sentiment from JSON (assuming the API returns { "label": "NEGATIVE", "score": 0.85 })
                    //we want the score SentimentResult.setLabel , SentimentResult.setSc
                    
                    if (jsonResponse.contains("\"label\":\"NEGATIVE\"")) {
                    	
                    	
                        return generatePositiveMessage(text);
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
    // Generate a positive response v1
/*    private String generatePositiveMessage(String originalText) {
        String[] messages = {
            "Stay strong! Every challenge is a chance to grow. 💪",
            "You're not alone! Better days are ahead. ☀️",
            "Every setback is a setup for a comeback. Keep going! 🚀",
            "Hard times don't last, but strong people do. You've got this! 💖"
        };
        return messages[new Random().nextInt(messages.length)];
    }
        */
    // Generate a contextual positive response v2
    private String generatePositiveMessage(String text) {
        Map<String, String[]> responseMap = new HashMap<>();
        
        responseMap.put("job|rent|money|bills|broke", new String[]{
            "Finances are tough, but remember: every setback is a setup for a comeback! 💪",
            "You're capable of overcoming financial struggles. Stay strong and keep pushing forward! 🌟",
            "Your worth isn't defined by money. Stay hopeful—better opportunities are ahead! ✨"
        });

        responseMap.put("sad|depressed|lonely|hopeless|worthless", new String[]{
            "You're not alone in this. Reach out, talk to someone, and know that you are valued. ❤️",
            "Dark times don’t last forever. Hold on, because brighter days are coming. ☀️",
            "You are stronger than you think. Keep going, because the world needs you! 🌍"
        });

        responseMap.put("breakup|heartbroken|alone|lost love", new String[]{
            "Breakups are hard, but this is also a chance for self-growth. You will heal. 💖",
            "Love yourself first. The right person will come when the time is right. 🌹",
            "Heartbreaks make us stronger. Take this time to focus on you! ✨"
        });

        responseMap.put("hate|fail|stupid|useless|waste", new String[]{
            "You are more capable than you know. Believe in yourself and keep pushing forward! 🚀",
            "Failures are lessons in disguise. Keep learning, keep growing! 📚",
            "You have a purpose, and you matter. Never let negativity define you! 💙"
        });

        for (Map.Entry<String, String[]> entry : responseMap.entrySet()) {
            if (text.matches(".*\\b(" + entry.getKey() + ")\\b.*")) {
                String[] messages = entry.getValue();
                return messages[new Random().nextInt(messages.length)];
            }
        }

        // Default response if no match found
        return "Stay strong! Every challenge is a step towards a better you. Keep going! 💪"; // private BigDecimal score;   // 0–1
    }
    
    public SentimentAnalysis analyzeText(String text) {
    	 String label ="";
	     BigDecimal score = new BigDecimal("0.85");
	    try {
	    	 // Flask API URL (check correct port!)
	        URL url = new URL("http://localhost:5000/analyze");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
	        // Configure HTTP request
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setDoOutput(true);
	
	        // JSON Request Payload
	        String jsonInput = "{\"text\": \"" + text + "\"}";
	
	        // Write JSON to request body
	        try (OutputStream os = conn.getOutputStream()) {
	            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
	        }
	        
	        int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                    String jsonResponse = scanner.useDelimiter("\\A").next();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(jsonResponse);

                     label = rootNode.get("label").asText();
                     score = rootNode.get("score").decimalValue();
                    // Extract sentiment from JSON (assuming the API returns { "label": "NEGATIVE", "score": 0.85 })
                    //we want the score SentimentResult.setLabel , SentimentResult.setScore
                   
                   
                }
            } else {
            	 label = "NEUTRAL"; //should add FAILED as part of enum
                 score =new BigDecimal("0.5");
            }
	       
	    } catch (Exception e) {
	             e.printStackTrace();
	             //return "Error: Could not connect to Flask API.";
	             label = "NEUTRAL"; //should add FAILED as part of enum
                 score =new BigDecimal("0.5");
	     }
	        
        return new SentimentAnalysis(label, score);
    }

}
