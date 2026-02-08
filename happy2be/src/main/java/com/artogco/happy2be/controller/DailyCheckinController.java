package com.artogco.happy2be.controller;



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


import com.artogco.happy2be.repository.CheckinRepository;
import com.artogco.happy2be.service.*;
import com.artogco.happy2be.service.EmotionService;
import com.artogco.happy2be.domain.SentimentAnalysis;
import com.artogco.happy2be.dto.*;
import com.artogco.happy2be.model.DailyCheckin;


@RestController
@RequestMapping("/daily")
public class DailyCheckinController {

    private final EmotionService emotionService;
    private final CheckinResponseService responseService;
    private final CheckinRepository checkinRepository;

    public DailyCheckinController(
        EmotionService emotionService,
        CheckinResponseService responseService,
        CheckinRepository checkinRepository
    ) {
        this.emotionService = emotionService;
        this.responseService = responseService;
        this.checkinRepository = checkinRepository;
    }

    @PostMapping("/checkin")
    public ResponseEntity<DailyCheckinResponse> processCheckIn(@RequestBody CheckinRequest req) {

        SentimentAnalysis sentiment =
            (SentimentAnalysis) emotionService.analyzeText(req.getUserText()); 
		
		DailyCheckinResponse  response = responseService.respond(req.getUserText(), sentiment);
		
		checkinRepository.save(
				DailyCheckin.from(req, sentiment,response)
		);
		
		return ResponseEntity.ok(response);
    }

	
	
}
