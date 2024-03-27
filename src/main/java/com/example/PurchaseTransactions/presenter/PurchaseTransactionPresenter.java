package com.example.PurchaseTransactions.presenter;

import com.example.PurchaseTransactions.model.ConvertedPurchaseTransaction;

public class PurchaseTransactionPresenter {
	private ConvertedPurchaseTransaction convertedPurchaseTransaction;
	private String status;
	
	public PurchaseTransactionPresenter() {
		
	}
	
	public PurchaseTransactionPresenter(String status) {
		this.setStatus(status);
	}
	
	public PurchaseTransactionPresenter(ConvertedPurchaseTransaction convertedPurchaseTransaction, String status) {
		this.setConvertedPurchaseTransaction(convertedPurchaseTransaction);
		this.setStatus(status);
	}

	public ConvertedPurchaseTransaction getConvertedPurchaseTransaction() {
		return convertedPurchaseTransaction;
	}

	public void setConvertedPurchaseTransaction(ConvertedPurchaseTransaction convertedPurchaseTransaction) {
		this.convertedPurchaseTransaction = convertedPurchaseTransaction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
