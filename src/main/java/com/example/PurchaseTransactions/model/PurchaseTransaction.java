package com.example.PurchaseTransactions.model;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class PurchaseTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column	
	private String description;
	@Column
	private String transactionDate;
	@Column
	@DecimalMax("1000000.0") @DecimalMin("0.01") 
	private double purchaseAmount;


	
	public PurchaseTransaction(double amount, String transDate, String desc) {
		this.purchaseAmount = amount;
		this.transactionDate = transDate;
		this.description = desc;
	}
	
	public PurchaseTransaction() {
		
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
}
