package com.artogco.happy2be.model;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "worksheet_submissions")
public class WorksheetSubmission {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String worksheetSlug;

    private String category;

    @Column(nullable = false)
    private Instant submittedAt;
    
    @Column(columnDefinition = "json")
    private String responses;


    private Integer version = 1;
    
    

    @Transient
    private Map<String, Object> responseMap;

    private static final ObjectMapper mapper = new ObjectMapper();

    /* ---------- JSON helpers ---------- */

    @PostLoad
    private void deserializeResponses() throws JsonProcessingException {
        if (responses != null) {
            responseMap = mapper.readValue(responses, Map.class);
        }
    }

    @PrePersist
    @PreUpdate
    private void serializeResponses() throws JsonProcessingException {
        if (responseMap != null) {
            responses = mapper.writeValueAsString(responseMap);
        }
    }
    

    public WorksheetSubmission() {}

    // getters + setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getWorksheetSlug() { return worksheetSlug; }
    public void setWorksheetSlug(String worksheetSlug) { this.worksheetSlug = worksheetSlug; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }

    public Map<String, Object> getResponses() { return responseMap; }
    public void setResponses(Map<String, Object> responses) { this.responseMap = responses; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
