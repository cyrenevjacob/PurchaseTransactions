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
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

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
	void greetingShouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("This is home!")));
	}
	
	@Test
	void testGetPurchaseTransaction() throws Exception{
		// Arrange
		UUID uuid = UUID.fromString("67739ab0-f2c1-4ae2-8611-7dc689e42138");
		// Act
		ResultActions result = mockMvc.perform(get("/get/{uuid}", uuid));
		// Assert
		result.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.transactionId").exists());
		
	}


	@Test
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
    public void testListPurchaseTransactions() throws Exception {
        // Arrange
		// Act
        ResultActions result = mockMvc.perform(get("/list")
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        result.andExpect(status().isOk());
    }

}