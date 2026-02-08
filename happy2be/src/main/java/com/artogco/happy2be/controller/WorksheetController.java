package com.artogco.happy2be.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artogco.happy2be.dto.WorksheetSubmissionRequest;
import com.artogco.happy2be.model.WorksheetSubmission;
import com.artogco.happy2be.service.WorksheetService;


@RestController
@RequestMapping("/api/workbooks")
public class WorksheetController {

    private final WorksheetService worksheetService;

    public WorksheetController(WorksheetService worksheetService) {
        this.worksheetService = worksheetService;
    }

    /**
     * POST /api/workbooks/worksheet
     */
    @PostMapping("/worksheet")
    public ResponseEntity<Map<String, Object>> saveWorksheet(
            @RequestBody WorksheetSubmissionRequest payload
    ) {
        // TEMP: replace with Spring Security later
        UUID fakeUserId = UUID.randomUUID();

        WorksheetSubmission saved =
                worksheetService.saveSubmission(payload, fakeUserId);

        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("status", "saved");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
	
	
	
	
}