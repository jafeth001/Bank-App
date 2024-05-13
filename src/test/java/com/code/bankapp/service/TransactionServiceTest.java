package com.code.bankapp.service;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import com.code.bankapp.model.TransactionType;
import com.code.bankapp.repository.BankAccountRepository;
import com.code.bankapp.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private BankAccountRepository bankAccountRepository;
    private BankTransactions bankTransactions;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = BankAccount
                .builder()
                .id(1L)
                .accountNumber(18904L)
                .active(true)
                .balance(1000.00)
                .build();
        Mockito.when(bankAccountRepository.findById(account.getId()))
                .thenReturn(Optional.ofNullable(account));
    }

    @Test
    @DisplayName("Junit test for deposit amount to account")
    void whenTransactionMade_thenDepositAmount() throws ConflictException, NoSuchException {
        bankTransactions = BankTransactions
                .builder()
                .amount(500.00)
                .transactionID("ABCKIL1920")
                .transactionPeriod(LocalDateTime.now())
                .transactionType(TransactionType.DEPOSIT)
                .account(account)
                .build();
        Mockito.when(transactionRepository.save(bankTransactions)).thenReturn(bankTransactions);
        transactionService.depositToAccount(bankTransactions, 1L);
        double balance = 1500.00;
        assertEquals(account.getBalance(), balance);

    }

    @Test
    @DisplayName("Junit test for withdraw amount to account")
    void whenTransactionMade_thenWithdrawAmount() throws ConflictException, NoSuchException {
        bankTransactions = BankTransactions
                .builder()
                .amount(500.00)
                .transactionID("ABCKIL1920")
                .transactionPeriod(LocalDateTime.now())
                .transactionType(TransactionType.WITHDRAW)
                .account(account)
                .build();
        Mockito.when(transactionRepository.save(bankTransactions)).thenReturn(bankTransactions);
        transactionService.withdrawFromAccount(bankTransactions, 1L);
        double balance = 500.00;
        assertEquals(account.getBalance(), balance);
    }

    @Test
    @DisplayName("Junit test for withdraw amount to account")
    void whenTransactionMade_thenTransferAmount() throws ConflictException, NoSuchException {
        BankAccount destAcc = BankAccount
                .builder()
                .id(2L)
                .accountNumber(18324L)
                .active(true)
                .balance(800.00)
                .build();
        Mockito.when(bankAccountRepository.findById(destAcc.getId()))
                .thenReturn(Optional.ofNullable(destAcc));

        bankTransactions = BankTransactions
                .builder()
                .amount(500.00)
                .transactionID("ABCKIL1920")
                .transactionPeriod(LocalDateTime.now())
                .transactionType(TransactionType.TRANSFER)
                .account(account)
                .build();
        Mockito.when(transactionRepository.save(bankTransactions)).thenReturn(bankTransactions);
        transactionService.transferFund(1L, 2L, bankTransactions);
        double accountBalance = 500.00;
        double destAccBalance = 1300.00;
        assertEquals(account.getBalance(), accountBalance);
        assertEquals(destAcc.getBalance(), destAccBalance);

    }
}