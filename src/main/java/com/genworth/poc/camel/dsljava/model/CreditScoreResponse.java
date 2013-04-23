package com.genworth.poc.camel.dsljava.model;

import java.io.Serializable;

/**
 * @author 501296262
 * Represents the response of a credit request. Used for both
 * OMNI and CB requests. The borrowerFormId is what ties all borrower
 * requests together - used to calculate a final score.
 */
public class CreditScoreResponse implements Serializable {
	private static final long serialVersionUID = 1685470254271446128L;
	private int borrowerFormId;
	private String creditScore;
	
	public CreditScoreResponse() {		
	}
	
	/*
	 * @param String formId
	 */
	public CreditScoreResponse(int formId) {
		this.borrowerFormId = formId;
	}
	
	public int getBorrowerFormId() {
		return borrowerFormId;
	}
	public void setBorrowerFormId(int formId) {
		this.borrowerFormId = formId;
	}
	
	public String getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}
}