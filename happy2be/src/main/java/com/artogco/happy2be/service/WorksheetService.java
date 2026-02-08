package com.artogco.happy2be.service;


import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.artogco.happy2be.dto.WorksheetSubmissionRequest;
import com.artogco.happy2be.model.WorksheetSubmission;
import com.artogco.happy2be.repository.WorksheetSubmissionRepository;

@Service
public class WorksheetService {


    private final WorksheetSubmissionRepository repository;

    public WorksheetService(WorksheetSubmissionRepository repository) {
        this.repository = repository;
    }

    public WorksheetSubmission saveSubmission(
            WorksheetSubmissionRequest request,
            UUID userId
    ) {
        WorksheetSubmission submission = new WorksheetSubmission();

        submission.setUserId(userId);
        submission.setWorksheetSlug(request.getWorksheetSlug());
        submission.setResponses(request.getResponses());
        submission.setSubmittedAt(Instant.now());
        submission.setVersion(1);

        return repository.save(submission);
    }
	
}
