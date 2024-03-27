package com.example.PurchaseTransactions.model;

import java.io.Serializable;
import java.util.UUID;


public class ConvertedPurchaseTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	private String description;
	
	private String transactionDate;
	
	private double purchaseAmount;
	
	private double convertedAmount;
	
	private double exchangeRate;
	
	private String recordDate;


	
	public ConvertedPurchaseTransaction(double amount, String transDate, String desc, UUID id, double convertedAmount, double exchangeRate, String recordDate) {
		this.purchaseAmount = amount;
		this.transactionDate = transDate;
		this.description = desc;
		this.id = id;
		this.convertedAmount = convertedAmount;
		this.exchangeRate = exchangeRate;
		this.recordDate = recordDate;
	}
	
	public ConvertedPurchaseTransaction() {
		
	}
	
	public double getTransactionAmount() {
		return this.purchaseAmount;
	}
	
	public void setTransactionAmount(double amount) {
		this.purchaseAmount = amount;
	}
	
	public String getTransactionDescription() {
		return this.description;
	}
	
	public void setTransactionDescription(String desc) {
		this.description = desc;
	}
	
	public String getTransactionDate() {
		return this.transactionDate;
	}
	
	public void setTransactionDate(String date) {
		this.transactionDate = date;
	}
	
	public UUID getTransactionId() {
		return this.id;
	}

	public double getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(double convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
}

