package com.artogco.happy2be.dto;

import java.math.BigDecimal;

public class SentimentResult {

	    private String label;   // POSITIVE | NEGATIVE | NEUTRAL
	    private BigDecimal score;   // 0–1
	    
	    
	    public SentimentResult() {}

	    public SentimentResult(String label, BigDecimal score) {
	        this.label = label;
	        this.score = score;
	    }
	    
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public BigDecimal getScore() {
			return score;
		}
		public void setScore(BigDecimal score) {
			this.score = score;
		}
	

}

