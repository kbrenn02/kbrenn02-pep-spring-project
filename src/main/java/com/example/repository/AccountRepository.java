package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Register new user done through JPA save() built in function

    // Log in a user
    Account findAccountByUsername(String username);

    // Get a user by ID
    Account findAccountByAccountId(Integer id);

}
