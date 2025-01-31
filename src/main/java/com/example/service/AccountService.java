package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service 
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Register a new user
    public Account registerUser(Account acct){
        // Registration parameters: username is not blank and the password is at least 4 characters long
        if(acct.getUsername() != null && !acct.getUsername().isEmpty() && acct.getPassword().length() >= 4){
            // Checks if this user already exists in the DB
            if(accountRepository.findAccountByUsername(acct.getUsername()) == null){
                return accountRepository.save(acct);
            }
        }
        return null; //registration is not successful for some other reason, the response status should be 400.
    }

    // Log in an existing user
    public Account loginUser(Account acct){
        Account retrievedAcct = accountRepository.findAccountByUsername(acct.getUsername());
        // Check if the retrieved account is null (username does not exist in the DB)
        if(retrievedAcct != null && acct.getPassword().equals(retrievedAcct.getPassword())){ // checks if the input password and the retrieved password are the same
            return retrievedAcct;
        }
        return null;
    }

    // Find account by username without checking the password
    public Account checkUser(Account acct){
        return accountRepository.findAccountByUsername(acct.getUsername());
    }

}
