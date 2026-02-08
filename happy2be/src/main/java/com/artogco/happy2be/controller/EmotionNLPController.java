package com.artogco.happy2be.controller;

import com.artogco.happy2be.service.EmotionNLPService;
import com.artogco.happy2be.service.NLPService;
import com.artogco.happy2be.service.OpenAIService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * Purpose: get a model response (OpenAI) to a textual  message using the NLPSentiment as it is a broader response service with category prompt
 * 
 */
@RestController
@RequestMapping("/api/emoai")
public class EmotionNLPController {

    @Autowired
    private final NLPService nlpService;
    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private final EmotionNLPService emotionNLPService;
    

    public EmotionNLPController(EmotionNLPService emotionService, NLPService aiResponseService,OpenAIService oOpenAIService) {
        this.emotionNLPService = emotionService;
        this.nlpService = aiResponseService;
        this.openAIService = oOpenAIService;
    }

    @PostMapping
    public String classifyEmotion(@RequestBody Map<String, String> payload) throws Exception {
        String text = payload.get("text");
        String tone = payload.getOrDefault("tone", "friendly");

        String sentimentResponse = emotionNLPService.analyzeNLPSentiment(text, tone);

        if (sentimentResponse.startsWith("Error")) {
            return openAIService.getAiHappy(text).block();
           // return nlpService.analyzeNLPSentiment("Give a supportive message for: " + text);
        }

        return sentimentResponse;
    }
}
