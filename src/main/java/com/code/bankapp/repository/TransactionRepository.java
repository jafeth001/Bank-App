package com.code.bankapp.repository;

import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransactions, Long> {
    Optional<BankTransactions> findBytransactionID(String transactionId);
}
