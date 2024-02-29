package com.code.bankapp.service;

import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.model.BankAccount;
import com.code.bankapp.model.Role;
import com.code.bankapp.model.User;
import com.code.bankapp.repository.BankAccountRepository;
import com.code.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final BankAccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    Random random = new Random();

    public BankAccount createAccount(Long id) throws NoSuchException {
        var existUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("user with id " + id + " not found"));
        BankAccount bankAcc = BankAccount
                .builder()
                .accountNumber(random.nextInt(999999))
                .balance(0.0)
                .user(existUser)
                .active(true)
                .build();
        accountRepository.save(bankAcc);
        return bankAcc;
    }

    public BankAccount getAccountById(Long id) throws NoSuchException {
        Optional<BankAccount> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        }
        throw new NoSuchException("bank account with id " + id + " not found");
    }

    public String deactivate(Long id) throws NoSuchException {
        BankAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("bank account with id " + id + " not found"));
        account.setActive(false);
        accountRepository.save(account);
        return "Account with id " + id + " deactivated...";
    }

    public String activate(Long id) throws NoSuchException {
        BankAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchException("bank account with id " + id + " not found"));
        account.setActive(true);
        accountRepository.save(account);
        return "Account with id " + id + " activated...";
    }

    public List<BankAccount> getAccounts() throws NoSuchException {
        var account = accountRepository.findAll();
        if (account.isEmpty()) {
            throw new NoSuchException("no transactions found");
        }
        return account;
    }

    public Optional<String> deleteAccount(Long id) {
        userRepository.deleteById(id);
        return Optional.of("user with id " + id + " deleted succesfully");
    }

    public List<User> findAllUsers() throws NoSuchException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoSuchException("no users available");
        }
        return users;
    }

    public BankAccount findAccountByAccountNo(Long accNo) throws NoSuchException {
        BankAccount bankAccount = accountRepository.findByAccountNumber(accNo);
        if (bankAccount != null) {
            return bankAccount;
        }
        throw new NoSuchException("bank account with account number " + accNo + " not found");
    }
}