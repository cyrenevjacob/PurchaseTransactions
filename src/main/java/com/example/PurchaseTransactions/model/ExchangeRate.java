package com.example.PurchaseTransactions.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRate {
	@JsonProperty("data")
	public List<Rate> exchangeRates;
}



	  
