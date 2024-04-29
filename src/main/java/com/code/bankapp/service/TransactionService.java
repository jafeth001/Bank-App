package com.code.bankapp.service;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.BankTransactions;
import com.code.bankapp.model.TransactionType;
import com.code.bankapp.model.User;
import com.code.bankapp.repository.BankAccountRepository;
import com.code.bankapp.repository.TransactionRepository;
import com.code.bankapp.repository.UserRepository;
import com.code.bankapp.util.RandomTransactionID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomTransactionID transactionID;

    public BankTransactions depositToAccount(BankTransactions bankTransactions, Long id)
            throws NoSuchException, ConflictException {
        BankAccount bankaccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("bank account with id " + id + " not found"));
        if (bankaccount.isActive()) {
            var transactions = BankTransactions
                    .builder()
                    .amount(bankTransactions.getAmount())
                    .transactionID(transactionID.getSaltString())
                    .transactionPeriod(LocalDateTime.now())
                    .account(bankaccount)
                    .transactionType(TransactionType.DEPOSIT)
                    .build();
            if (bankTransactions.getAmount() <= 0) {
                throw new ConflictException("amount must be more than 0");
            }
            bankaccount.setBalance(bankaccount.getBalance() + transactions.getAmount());
            bankAccountRepository.save(bankaccount);
            return transactionRepository.save(transactions);
        }
        throw new ConflictException("account not active... ");
    }

    public BankTransactions withdrawFromAccount(BankTransactions bankTransactions, Long id)
            throws NoSuchException, ConflictException {
        BankAccount bankaccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("bank account with id " + id + " not found"));
        if (bankaccount.isActive()) {
            var transactions = BankTransactions
                    .builder()
                    .amount(bankTransactions.getAmount())
                    .transactionID(transactionID.getSaltString())
                    .transactionPeriod(LocalDateTime.now())
                    .account(bankaccount)
                    .transactionType(TransactionType.WITHDRAW)
                    .build();
            if (bankTransactions.getAmount() <= 0) {
                throw new ConflictException("amount must be more than 0");
            } else if (bankaccount.getBalance() < bankTransactions.getAmount()) {
                throw new ConflictException("insufficient amount to complete transaction... your bank balance is " + bankaccount.getBalance());
            }
            bankaccount.setBalance(bankaccount.getBalance() - transactions.getAmount());
            bankAccountRepository.save(bankaccount);
            return transactionRepository.save(transactions);
        }
        throw new ConflictException("account not active...");
    }

    public BankTransactions transferFund(Long id, Long destId, BankTransactions bankTransactions)
            throws NoSuchException, ConflictException {
        BankAccount bankaccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("bank account with id " + id + " not found"));
        BankAccount destinationAcc = bankAccountRepository.findById(destId)
                .orElseThrow(() -> new NoSuchException("destination bank account with id " + destId + " not found"));
        if (bankaccount.isActive()) {
            var transactions = BankTransactions
                    .builder()
                    .amount(bankTransactions.getAmount())
                    .transactionPeriod(LocalDateTime.now())
                    .transactionID(transactionID.getSaltString())
                    .account(bankaccount)
                    .transactionType(TransactionType.TRANSFER)
                    .build();
            if (bankTransactions.getAmount() <= 0) {
                throw new ConflictException("amount must be more than 0");
            } else if (bankaccount.getBalance() < bankTransactions.getAmount()) {
                throw new ConflictException("insufficient amount to complete transaction... your bank balance is " + bankaccount.getBalance());
            }
            bankaccount.setBalance(bankaccount.getBalance() - bankTransactions.getAmount());
            destinationAcc.setBalance(destinationAcc.getBalance() + bankTransactions.getAmount());
            bankAccountRepository.save(bankaccount);
            bankAccountRepository.save(destinationAcc);
            return transactionRepository.save(transactions);
        }
        throw new ConflictException("account not active...");
    }

    public List<BankTransactions> getAllTransactions() throws NoSuchException {
        var transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new NoSuchException("no transactions found");
        }
        return transactions;
    }

    public Optional<BankTransactions> bankTransaction(Long id) {
        return transactionRepository.findById(id);
    }

    public Optional<BankTransactions> getBytransactionId(String transactionId) {
        return transactionRepository.findBytransactionID(transactionId);
    }
}
