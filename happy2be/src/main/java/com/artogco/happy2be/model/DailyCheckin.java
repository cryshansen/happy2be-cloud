package com.artogco.happy2be.model;
import java.time.Instant;

import java.util.UUID;

import com.artogco.happy2be.domain.SentimentAnalysis;
import com.artogco.happy2be.dto.CheckinRequest;
import com.artogco.happy2be.dto.DailyCheckinResponse;

import java.math.BigDecimal;
import jakarta.persistence.*;


@Entity
@Table(name="check_ins")
public class DailyCheckin {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String dailySlug;


    
    @Column(columnDefinition = "TEXT")
    private String userInitalText;
    
    @Column(columnDefinition = "TEXT")
    private String gratitudeText;
    
    @Column() //ENUM('POSITIVE', 'NEUTRAL', 'NEGATIVE')
    private String sentimentString;
    @Column(precision=4,scale=3)
    private BigDecimal sentimentScore;
  
    @Column(columnDefinition = "TEXT")
    private String systemResponses;
    @Column(nullable = false)
    private Instant submittedAt;
    
    private Integer version = 1;
    // 👉 Relationship
    @OneToOne(mappedBy = "checkIn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CheckInMetadata metadata;
    
 
	
	
	public DailyCheckin() {}
	public static DailyCheckin from(
	        CheckinRequest req,
	        SentimentAnalysis sentiment,
	        DailyCheckinResponse response
	) {
	    DailyCheckin checkin = new DailyCheckin();

	//    checkin.setUserId(req.getUserId());
	    checkin.setDailySlug("daily-checkin");

	    checkin.setUserInitalText(req.getUserText());
	//    checkin.setGratitudeText(req.getGratitudeText());

	    // internal-only storage
	    checkin.setSentimentString(sentiment.label());
	    checkin.setSentimentScore(sentiment.score());

	    // what the system said back to the user
	    checkin.setSystemResponses(response.getMessage());

	    checkin.setSubmittedAt(Instant.now());
	    checkin.setVersion(1);
	    // 🔗 METADATA
	    CheckInMetadata metadata = new CheckInMetadata();
	    metadata.setCheckIn(checkin);

	  //  metadata.setNeedsFollowUp(sentiment.score().doubleValue() < -0.4);
	    //metadata.setCrisisFlag(sentiment.score().doubleValue() < -0.7);

	    // future-proof: JSON string for distortions
	   // metadata.setDistortions(sentiment..distortionsJson());
	   // metadata.setAnalysisVersion(1);

	    checkin.setMetadata(metadata);
	    return checkin;
	}
	

	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public UUID getUserId() {
		return userId;
	}



	public void setUserId(UUID userId) {
		this.userId = userId;
	}



	public String getDailySlug() {
		return dailySlug;
	}



	public void setDailySlug(String dailySlug) {
		this.dailySlug = dailySlug;
	}



	public String getUserInitalText() {
		return userInitalText;
	}



	public void setUserInitalText(String userInitalText) {
		this.userInitalText = userInitalText;
	}



	public String getGratitudeText() {
		return gratitudeText;
	}



	public void setGratitudeText(String gratitudeText) {
		this.gratitudeText = gratitudeText;
	}



	public String getSentimentString() {
		return sentimentString;
	}



	public void setSentimentString(String sentimentString) {
		this.sentimentString = sentimentString;
	}



	public BigDecimal getSentimentScore() {
		return sentimentScore;
	}



	public void setSentimentScore(BigDecimal sentimentScore) {
		this.sentimentScore = sentimentScore;
	}



	public String getSystemResponses() {
		return systemResponses;
	}



	public void setSystemResponses(String systemResponses) {
		this.systemResponses = systemResponses;
	}



	public Instant getSubmittedAt() {
		return submittedAt;
	}



	public void setSubmittedAt(Instant submittedAt) {
		this.submittedAt = submittedAt;
	}



	public Integer getVersion() {
		return version;
	}



	public void setVersion(Integer version) {
		this.version = version;
	}



	public CheckInMetadata getMetadata() {
		return metadata;
	}



	public void setMetadata(CheckInMetadata metadata) {
		this.metadata = metadata;
	}




	
	

}
