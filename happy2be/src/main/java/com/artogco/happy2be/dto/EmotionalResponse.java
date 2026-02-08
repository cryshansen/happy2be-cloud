package com.artogco.happy2be.dto;

public class EmotionalResponse {

    private String message;
    private boolean alert;

    public EmotionalResponse(String message, boolean alert) {
        this.message = message;
        this.alert = alert;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAlert() {
        return alert;
    }
}
