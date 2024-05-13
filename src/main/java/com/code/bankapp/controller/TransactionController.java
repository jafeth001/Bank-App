package com.code.bankapp.controller;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.kafka.producer.KafkaProducer;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import com.code.bankapp.model.User;
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
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public Optional<BankTransactions> bankTransaction(@AuthenticationPrincipal User user, @RequestParam Long id) {
        return transactionService.bankTransaction(id);
    }

    @GetMapping("/{transactionId}")
    public Optional<BankTransactions> getBytransactionId(@AuthenticationPrincipal User user, @PathVariable String transactionId) {
        return transactionService.getBytransactionId(transactionId);
    }

    @GetMapping("/all")
    public List<BankTransactions> getAllTransactions(@AuthenticationPrincipal User user) throws NoSuchException {
        return transactionService.getAllTransactions();
    }

    @PutMapping("/deposit")
    public BankTransactions depositToAccount
            (@AuthenticationPrincipal User user, @RequestBody BankTransactions bankTransactions,
             @RequestParam Long id) throws NoSuchException, ConflictException {
        return transactionService.depositToAccount(bankTransactions, id);
    }

    @PutMapping("/withdraw")
    public BankTransactions withdrawFromAccount
            (@AuthenticationPrincipal User user, @RequestBody BankTransactions bankTransactions,
             @RequestParam Long id) throws NoSuchException, ConflictException {
        return transactionService.withdrawFromAccount(bankTransactions, id);
    }

    @PutMapping("/transfer")
    public BankTransactions transferFund
            (@AuthenticationPrincipal User user, @RequestBody BankTransactions bankTransactions,
             @RequestParam Long id, @RequestParam Long destId)
            throws NoSuchException, ConflictException {
        return transactionService.transferFund(id, destId, bankTransactions);
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
