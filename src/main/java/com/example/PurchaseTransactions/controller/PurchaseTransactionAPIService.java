package com.example.PurchaseTransactions.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.PurchaseTransactions.model.ConvertedPurchaseTransaction;
import com.example.PurchaseTransactions.model.ExchangeRate;
import com.example.PurchaseTransactions.model.PurchaseTransaction;
import com.example.PurchaseTransactions.presenter.PurchaseTransactionPresenter;
import com.example.PurchaseTransactions.repo.PurchaseTransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
public class PurchaseTransactionAPIService {
	
	@Autowired
	private PurchaseTransactionRepo purchaseTransactionRepo;
	
	@Value("${app.fiscalurl}")
    private String fURL;
	
	@Value("${app.backdatemonths}")
    private int backdateByMonths;
	
	@GetMapping(value = "/")
	public String getHome() {
		return "Welcome. This was written by ChatGPT. Nah, just kidding! This is home!";
	}
	
	@GetMapping(value = "/list")
	public List<PurchaseTransaction> getAllTransactions() {
		return purchaseTransactionRepo.findAll();
		
	}
	
	@GetMapping(value = "/get/{uuid}")
	//public List<Object> getTransaction(@PathVariable UUID uuid) {
	public PurchaseTransaction getTransaction(@PathVariable UUID uuid) throws JsonMappingException, JsonProcessingException {
		PurchaseTransaction purchaseTransaction = purchaseTransactionRepo.findById(uuid).get();
		return purchaseTransaction;
	}
	
	@GetMapping(value = "/get/{uuid}/{countrycurrency}")
	//public List<Object> getTransaction(@PathVariable UUID uuid) {
	public PurchaseTransactionPresenter getExchangeValueForTransaction(@PathVariable UUID uuid, @PathVariable String countrycurrency) throws JsonMappingException, JsonProcessingException {
		PurchaseTransaction purchaseTransaction = purchaseTransactionRepo.findById(uuid).get();
		double purchaseAmount = purchaseTransaction.getTransactionAmount();
		LocalDate transactionDate = LocalDate.parse(purchaseTransaction.getTransactionDate());
		LocalDate transactionsFrom = transactionDate.minusMonths(backdateByMonths);
		String url = fURL.replace("XXXX-XXX", countrycurrency);
		url = url.replace("YYYY-MM-dd", transactionsFrom.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        String jsonString = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExchangeRate exchangeRateObj = mapper.readValue(jsonString, ExchangeRate.class);
        double exchangeRate = Double.parseDouble(exchangeRateObj.exchangeRates.get(0).exchangeRate);
        String recordDate = exchangeRateObj.exchangeRates.get(0).recordDate;
        double convertedAmount = exchangeRate * purchaseAmount;
        LocalDate recDate = LocalDate.parse(recordDate);
        if(recDate.isAfter(transactionDate)) return new PurchaseTransactionPresenter("Cannot be converted to target currency. Record Date is " + recDate.toString() + " and transaction on " + transactionDate.toString());
        
        ConvertedPurchaseTransaction convertedPurchaseTransaction = new ConvertedPurchaseTransaction(
        		purchaseAmount, 
        		purchaseTransaction.getTransactionDate(), 
        		purchaseTransaction.getTransactionDescription(),
        		purchaseTransaction.getTransactionId(),
        		convertedAmount,
        		exchangeRate,
        		recordDate
        		);
        
        return new PurchaseTransactionPresenter(convertedPurchaseTransaction, "Conversion successful. Record Date is " + recDate.toString() + " and transaction on " + transactionDate.toString());
        //return Double.toString(convertedAmount) + "(" + exchangeRateObj.exchangeRates.get(0).recordDate + ")";
	}

	
	@PostMapping(value = "/add")
	public String addTransaction(@Valid @RequestBody PurchaseTransaction purchaseTransaction){
		
		if(purchaseTransaction.getTransactionDescription().isEmpty()) return "Transaction description cannot be blank";
		
		try{
			LocalDate transactionDate = LocalDate.parse(purchaseTransaction.getTransactionDate());
	    }
	    catch(Exception ex){
	    	return "Transaction Date incorrect.";
	    }
	
		double transAmount = purchaseTransaction.getTransactionAmount();
		if(transAmount <= 0) return "Transaction amount can't be 0";
		
		double roundOff;
		roundOff = (double) Math.round(transAmount * 100) / 100;
        purchaseTransaction.setTransactionAmount(roundOff);
	    
		purchaseTransactionRepo.save(purchaseTransaction);
		return "Inserted Transaction.";
		
	}
	
	private String getCountryCurrency(String currency) {
		return currency;
	}
	
}
