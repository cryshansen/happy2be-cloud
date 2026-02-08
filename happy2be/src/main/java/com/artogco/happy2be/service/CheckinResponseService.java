package com.artogco.happy2be.service;


import org.springframework.stereotype.Service;

import com.artogco.happy2be.dto.EmotionalResponse;
import com.artogco.happy2be.dto.SentimentResult;

import com.artogco.happy2be.dto.*;
import com.artogco.happy2be.domain.*;

@Service
public class CheckinResponseService {

    public EmotionalResponse generate(String text, SentimentResult sentiment, String tone) {

        boolean alert = "NEGATIVE".equals(sentiment.getLabel())
                        && sentiment.getScore().doubleValue() > 0.8;

        String message;

        if (alert) {
            message = "It sounds like today was really heavy. You’re not alone.";
        } else if ("POSITIVE".equals(sentiment.getLabel())) {
            message = "Thanks for checking in — it sounds like you’re in a good place today.";
        } else {
            message = "Thanks for sharing. Awareness is a powerful step.";
        }

        return new EmotionalResponse(message, alert);
    }
    

    public DailyCheckinResponse respond(
            String text,
            SentimentAnalysis analysis
    ) {
        boolean alert =
            "NEGATIVE".equals(analysis.label())
            && analysis.score().doubleValue() > 0.85;

        String message;

        if (alert) {
            message = "It sounds like today was really heavy. I'm glad you checked in.";
        } else if ("POSITIVE".equals(analysis.label())) {
            message = "Thanks for sharing — it sounds like today had some lighter moments.";
        } else {
            message = "Thanks for checking in. Noticing your thoughts is a powerful step.";
        }

        return new DailyCheckinResponse(message, alert);
    }
}
