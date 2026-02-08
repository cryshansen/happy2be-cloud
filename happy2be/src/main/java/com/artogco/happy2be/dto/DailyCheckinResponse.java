package com.artogco.happy2be.dto;

public class DailyCheckinResponse {


    private String message;
    private boolean alert;

    public DailyCheckinResponse(String message, boolean alert) {
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
