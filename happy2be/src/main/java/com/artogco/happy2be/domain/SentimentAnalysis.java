package com.artogco.happy2be.domain;

import java.math.BigDecimal;

public class SentimentAnalysis {

	private final String label;
	private final BigDecimal score;
	
	public SentimentAnalysis( String label, BigDecimal score) {
		this.label = label;
		this.score = score;
	}
	
	public String label() { return label; }
	public BigDecimal score() {return score;}
	
	
}
