package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {
    
    private AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> registerAccount(Account newAccount) {
        
        // If username is not blank, password is at least 4 characters, and the account name does not already exist,
        //      then the account can be registered
        // Should return JSON of account
        if (newAccount.getUsername() != null && newAccount.getPassword().length() >= 4 
            && !accountRepository.existsByUsername(newAccount.getUsername())) {
                Account registeredAccount = accountRepository.save(newAccount);
                return Optional.of(registeredAccount);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Account> loginAccount(Account loginAccount) {
        
        // Login should be successful if a username and password has matching credentials to account in database
        // Should return JSON of account
        Optional<Account> existingAccount = accountRepository.findByUsername(loginAccount.getUsername());

        if (existingAccount.isPresent() && existingAccount.get().getPassword().equals(loginAccount.getPassword())) {
            return existingAccount;
        } else {
            return Optional.empty();
        }
    }
}
