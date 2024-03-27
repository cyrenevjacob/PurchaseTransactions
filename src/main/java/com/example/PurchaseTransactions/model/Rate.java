package com.example.PurchaseTransactions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rate {
	@JsonProperty("country_currency_desc") public String countryCurrency;
	@JsonProperty("exchange_rate") public String exchangeRate;
	@JsonProperty("record_date") public String recordDate;
}
