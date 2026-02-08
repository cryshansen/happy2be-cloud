package com.artogco.happy2be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


import com.artogco.happy2be.model.WorksheetSubmission;

public interface WorksheetSubmissionRepository extends JpaRepository<WorksheetSubmission , UUID> {

}
