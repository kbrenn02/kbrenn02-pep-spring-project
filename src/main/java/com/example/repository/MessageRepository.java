package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Return message by ID but ID is an int
    Message findMessageByMessageId(Integer id);
}
