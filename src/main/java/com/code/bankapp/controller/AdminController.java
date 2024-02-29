package com.code.bankapp.controller;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import com.code.bankapp.model.User;
import com.code.bankapp.service.AccountService;
import com.code.bankapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/users")
    public List<User> users(@AuthenticationPrincipal User user) throws NoSuchException {
        return accountService.findAllUsers();
    }

    @GetMapping("/account")
    public BankAccount getBankAccount(@AuthenticationPrincipal User user, @RequestParam Long accNo) throws NoSuchException {
        return accountService.findAccountByAccountNo(accNo);
    }

    @PutMapping("/deactivate")
    public String deactivateBankAccount(@AuthenticationPrincipal User user, @RequestParam Long id) throws NoSuchException {
        return accountService.deactivate(id);
    }

    @PutMapping("/activate")
    public String activateBankAccount(@AuthenticationPrincipal User user, @RequestParam Long id) throws NoSuchException {
        return accountService.activate(id);
    }

    @DeleteMapping("/delete")
    public Optional<String> deleteAccount(@AuthenticationPrincipal User user, @RequestParam Long id) {
        return accountService.deleteAccount(id);
    }
}
