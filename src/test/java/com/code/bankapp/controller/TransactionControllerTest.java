package com.code.bankapp.controller;

import com.code.bankapp.config.JwtService;
import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import com.code.bankapp.model.TransactionType;
import com.code.bankapp.model.User;
import com.code.bankapp.service.AccountService;
import com.code.bankapp.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountService accountService;
    private BankTransactions bankTransactions;
    private BankAccount bankAccount;
    

    @BeforeEach
    void setup() throws NoSuchException {
        bankAccount = BankAccount
                .builder()
                .id(1L)
                .accountNumber(18904L)
                .active(true)
                .balance(1000.00)

                .build();
        Mockito.when(accountService.getAccountById(bankAccount.getId()))
                .thenReturn(bankAccount);
    }

    @Test
    void depositToAccount() throws Exception {
        bankTransactions = BankTransactions
                .builder()
                .amount(500.00)
                .transactionID("ABCKIL1920")
                .transactionPeriod(LocalDateTime.now())
                .transactionType(TransactionType.DEPOSIT)
                .account(bankAccount)
                .build();
        Mockito.when(transactionService.depositToAccount(bankTransactions, 1L))
                .thenReturn(bankTransactions);
        mockMvc.perform(put("/transaction/deposit?id=1L")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bankTransactions)))
                .andExpect(status().isCreated());
        assertEquals(bankAccount.getBalance(),1500.00);

    }

    @Test
    void withdrawFromAccount() {
    }

    @Test
    void transferFund() {
    }
}