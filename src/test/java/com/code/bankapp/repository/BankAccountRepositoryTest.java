package com.code.bankapp.repository;

import com.code.bankapp.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TestEntityManager entityManager;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = BankAccount.builder()
                .accountNumber(12463L)
                .balance(1200.00)
                .build();
        entityManager.persist(bankAccount);
    }

    @Test
    @DisplayName("Junit test for findByAccountNumber method")
    void whenAccNumberFound_thenReturnBankAcc() {
        Long bankAcc = 12463L;
        BankAccount found = bankAccountRepository.findByAccountNumber(bankAcc);
        assertEquals(found.getAccountNumber(),bankAcc);
    }
}