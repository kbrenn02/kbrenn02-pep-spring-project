package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Delete message by ID but ID is an int
    // @Modifying
    // @Transactional
    // @Query("DELETE FROM message m WHERE m.messageId = :id")
    // int deleteMessageByMessageId(@Param("id") int id);
}
