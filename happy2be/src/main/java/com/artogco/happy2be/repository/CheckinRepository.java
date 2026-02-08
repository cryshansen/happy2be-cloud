package com.artogco.happy2be.repository;

import com.artogco.happy2be.model.DailyCheckin;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<DailyCheckin, UUID> {

}
