package com.code.bankapp.controller;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.User;
import com.code.bankapp.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createAccount
            (@AuthenticationPrincipal User user, @RequestParam Long id)
            throws NoSuchException {
        log.info("creating bank account for user : {}",user);
        return ResponseEntity.ok(accountService.createAccount(id));
    }
    @GetMapping("/all")
    public List<BankAccount> getAccounts(@AuthenticationPrincipal User user) throws NoSuchException {
        return accountService.getAccounts();
    }

   @GetMapping
    public ResponseEntity<BankAccount> getAccountById(@AuthenticationPrincipal User user,@RequestParam Long id)
           throws NoSuchException {
        log.info("getting bank account with id: {}",id);
        return ResponseEntity.ok(accountService.getAccountById(id));
   }

}
