package com.code.bankapp.repository;

import com.code.bankapp.model.BankTransactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TestEntityManager entityManager;
    private BankTransactions bankTransactions;

    @BeforeEach
    void setUp() {
        bankTransactions = BankTransactions
                .builder()
                .transactionID("ABWQRT1274")
                .build();
        entityManager.persist(bankTransactions);
    }

    @Test
    @DisplayName("Junit test for findBytransactionID method")
    void whentransactionIdFound_thenreturnBankTransactions() {
        String transactionId = "ABWQRT1274";
        Optional<BankTransactions> found = transactionRepository.findBytransactionID(transactionId);
        assertEquals(found.get().getTransactionID(),transactionId);
    }
}