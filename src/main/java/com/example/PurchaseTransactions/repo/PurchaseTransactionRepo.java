package com.example.PurchaseTransactions.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PurchaseTransactions.model.PurchaseTransaction;

public interface PurchaseTransactionRepo extends JpaRepository<PurchaseTransaction, UUID> {

}
