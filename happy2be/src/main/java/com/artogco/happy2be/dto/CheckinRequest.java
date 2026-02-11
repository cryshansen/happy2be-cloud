
package com.artogco.happy2be.dto;

import java.util.UUID;

public class CheckinRequest{
	
	private String userText;
	private String tone;
	private UUID userId;
	
	
	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}
	
	public UUID getUserId() {
		return userId;
	}



	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}