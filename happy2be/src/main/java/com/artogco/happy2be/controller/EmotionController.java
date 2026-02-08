package com.artogco.happy2be.controller;

import com.artogco.happy2be.dto.TextRequest;
import com.artogco.happy2be.service.EmotionService;
import com.artogco.happy2be.service.PatternService;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emotion")
public class EmotionController {

    private final EmotionService emotionService;
    private final PatternService patternService;

    public EmotionController(EmotionService emotionService, PatternService patternService) {
        this.emotionService = emotionService;
        this.patternService = patternService;
    }

    /**
     * 🔹 Classic Sentiment Analysis — Phase 1
     * Accepts text input, returns sentiment classification (positive/negative/neutral)
     */
    @PostMapping("/classify")
    public ResponseEntity<Map<String, Object>> classifyEmotion(@RequestBody TextRequest request) {
        String sentiment = emotionService.analyzeSentiment(request.getText());
        Map<String, Object> response = new HashMap<>();
        response.put("sentiment", sentiment);
        return ResponseEntity.ok(response);
    }

    /**
     * 🔹 Phase 2: Mood Patterns from ChatGPT
     * Input: {"feeling": "drained"}
     * Output: {
     *   "feeling": "drained",
     *   "patterns": [...],
     *   "practices": [...]
     * }
     */
    @PostMapping("/patterns")
    public ResponseEntity<Map<String, Object>> generateMoodPatterns(@RequestBody Map<String, String> payload) {
        String feeling = payload.get("feeling");
        if (feeling == null || feeling.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing 'feeling' field"));
        }

        try {
            Map<String, Object> moodResponse = patternService.getMoodPatterns(feeling);
            return ResponseEntity.ok(moodResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error generating mood patterns"));
        }
    }

    /**
     * 🔹 Phase 3: Expand Unknown Feelings
     * Input: {"query": "frustrated"}
     * Output: {"base": "frustrated", "related": ["irritated", "blocked", ...]}
     */
    @PostMapping("/expand")
    public ResponseEntity<Map<String, Object>> expandFeeling(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing 'query' field"));
        }

        try {
            Map<String, Object> expansion = patternService.expandFeeling(query);
            return ResponseEntity.ok(expansion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error expanding feeling vocabulary"));
        }
    }

    /**
     * 🔹 Phase 4: Affirmations for a Feeling
     * Input: {"feeling": "anxious"}
     * Output: {"affirmations": [...]}
     */
    @PostMapping("/affirmations")
    public ResponseEntity<Map<String, Object>> generateAffirmations(@RequestBody Map<String, String> payload) {
        String feeling = payload.get("feeling");
        if (feeling == null || feeling.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing 'feeling' field"));
        }

        try {
            Map<String, Object> affirmations = patternService.getAffirmations(feeling);
            return ResponseEntity.ok(affirmations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error generating affirmations"));
        }
    }

    /**
     * 🔹 Phase 5: Reflect on Intensity
     * Input: {"feeling": "anxious", "intensity": 8}
     * Output: {"message": "It sounds like..."}
     */
    @PostMapping("/intensity")
    public ResponseEntity<Map<String, Object>> reflectOnIntensity(@RequestBody Map<String, Object> payload) {
        String feeling = (String) payload.get("feeling");
        Integer intensity = (Integer) payload.get("intensity");

        if (feeling == null || intensity == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing 'feeling' or 'intensity' field"));
        }

        try {
            Map<String, Object> reflection = patternService.getIntensityReflection(feeling, intensity);
            return ResponseEntity.ok(reflection);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error generating reflection"));
        }
    }
}

