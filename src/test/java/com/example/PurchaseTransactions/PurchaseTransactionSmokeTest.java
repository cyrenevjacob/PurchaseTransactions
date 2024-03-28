package com.example.PurchaseTransactions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
class PurchaseTransactionSmokeTest {
    
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Test if home page loads with custom message")
	void greetingShouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("This is home!")));
	}
	
	@Test
	@DisplayName("Insert Transaction")
    public void testAddPurchaseTransaction() throws Exception {
        // Arrange
		Random random = new Random();
	    double nthRandomNumber = 0.0;
	    nthRandomNumber = Math.random() + random.nextDouble();
	    DecimalFormat decimalFormat = new DecimalFormat("#.00");
	    String decFormatted = decimalFormat.format(nthRandomNumber);
        String purchaseJson = "{\"transactionAmount\":\"" + decFormatted + "\",\"transactionDate\":\"" + LocalDate.now().toString() + "\", \"transactionDescription\":\"Purchase for test on " + LocalDate.now().toString() + "\"}";
        // Act
        ResultActions result = mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseJson));
        // Assert
        result.andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Get all transactions - lists the transaction created above.")
    public void testListPurchaseTransactions() throws Exception {
        // Arrange
		// Act
        ResultActions result = mockMvc.perform(get("/list")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        result.andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Add Transaction with an invalid amount")
    public void testAddPurchaseTransactionInvalidAmount() throws Exception {
        // Arrange
        String purchaseJson = "{\"transactionAmount\":\"ABCD.XYZ\",\"transactionDate\":\"" + LocalDate.now().toString() + "\", \"transactionDescription\":\"Purchase for test on " + LocalDate.now().toString() + "\"}";
        // Act
        ResultActions result = mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseJson));
        // Assert
        result.andExpect(status().is4xxClientError());
    }
	
	@Test
	@DisplayName("Add Transaction with an invalid date")
    public void testAddPurchaseTransactionInvalidDate() throws Exception {
        // Arrange
		Random random = new Random();
	    double nthRandomNumber = 0.0;
	    nthRandomNumber = Math.random() + random.nextDouble();
	    DecimalFormat decimalFormat = new DecimalFormat("#.00");
	    String decFormatted = decimalFormat.format(nthRandomNumber);
        String purchaseJson = "{\"transactionAmount\":\"" + decFormatted + "\",\"transactionDate\":\"2024-31-89\", \"transactionDescription\":\"Purchase for test on " + LocalDate.now().toString() + "\"}";
        // Act and Assert
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseJson))
        		.andDo(print()).andExpect(status().isOk())
        		.andExpect(content().string(containsString("Transaction Date incorrect.")));
    }
	
	@Test
	@DisplayName("Add Transaction without description")
    public void testAddPurchaseTransactionEmptyDescription() throws Exception {
        // Arrange
		Random random = new Random();
	    double nthRandomNumber = 0.0;
	    nthRandomNumber = Math.random() + random.nextDouble();
	    DecimalFormat decimalFormat = new DecimalFormat("#.00");
	    String decFormatted = decimalFormat.format(nthRandomNumber);
        String purchaseJson = "{\"transactionAmount\":\"" + decFormatted + "\",\"transactionDate\":\"\"" + LocalDate.now().toString() + "\", \"transactionDescription\":\"\"}";
        // Act and Assert
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseJson))
        		.andDo(print()).andExpect(status().is4xxClientError());
    }
	
	@Test
	@DisplayName("Get Transaction with an invalid id")
	public void testGetPurchaseTransactionInvalid() throws Exception{
		// Arrange
		String uuid = "0389";
		// Act
		this.mockMvc.perform(get("/get/{uuid}", uuid)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.transactionAmount").value(0.0));

	}
   
}