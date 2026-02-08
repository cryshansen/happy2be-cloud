package com.artogco.happy2be.dto;



import java.util.Map;

public class WorksheetSubmissionRequest {

    private String worksheetSlug;
    private Map<String, Object> responses;

    public WorksheetSubmissionRequest() {}

    public String getWorksheetSlug() {
        return worksheetSlug;
    }

    public void setWorksheetSlug(String worksheetSlug) {
        this.worksheetSlug = worksheetSlug;
    }

    public Map<String, Object> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Object> responses) {
        this.responses = responses;
    }
}
