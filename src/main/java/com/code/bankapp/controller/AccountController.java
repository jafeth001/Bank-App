package com.code.bankapp.controller;

import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.kafka.producer.KafkaProducer;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.User;
import com.code.bankapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createAccount
            (@AuthenticationPrincipal User user, @RequestParam Long id)
            throws NoSuchException {
        log.info("creating bank account for user : {}", user);
        return ResponseEntity.ok(accountService.createAccount(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankAccount>> getAccounts(@AuthenticationPrincipal User user) throws NoSuchException {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping
    public ResponseEntity<BankAccount> getAccountById(@AuthenticationPrincipal User user, @RequestParam Long id)
            throws NoSuchException {
        log.info("getting bank account with id: {}", id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    /**
     * send message to kafka
     *
     * @param message
     * @return
     */
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
        log.info("message sent to kafka topic : {}", message);
        return ResponseEntity.ok("Message Successfully Queued to Kafka Topic");
    }
}
