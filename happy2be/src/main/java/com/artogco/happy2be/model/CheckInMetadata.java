package com.artogco.happy2be.model;



import java.util.UUID;

import jakarta.persistence.*;



@Entity
@Table(name = "check_in_metadata")
public class CheckInMetadata {

    @Id
    @Column(name = "check_in_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "check_in_id")
    private DailyCheckin checkIn;

    @Column(columnDefinition = "json")
    private String distortions;  // Cognitive Distortions

    @Column(columnDefinition = "TEXT")
    private String aiSummary;

    @Column(columnDefinition = "JSON")
    private String flags;  //crisis flages

    public CheckInMetadata() {}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public DailyCheckin getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(DailyCheckin checkIn) {
		this.checkIn = checkIn;
	}

	public String getDistortions() {
		return distortions;
	}

	public void setDistortions(String distortions) {
		this.distortions = distortions;
	}

	public String getAiSummary() {
		return aiSummary;
	}

	public void setAiSummary(String aiSummary) {
		this.aiSummary = aiSummary;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}
    
    
    
    
}
