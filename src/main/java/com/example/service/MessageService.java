package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import com.example.entity.Message;
import com.example.entity.Account;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Create new message
    public Message postMessage(Message message){
        if(message.getMessageText().length() <= 255 && !message.getMessageText().isEmpty()){
            Account existingUser = accountRepository.findById(message.getPostedBy()).orElse(null);
            if(existingUser != null){
                return messageRepository.save(message);
            }
        }

        return null;
    }

    // Get all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    // Get message by Id
    public Message getMessageById(int id){
        return messageRepository.findById(id).orElse(null);
    }

    // Delete message by Id
    public int deleteMessageById(int id){
        Optional<Message> messageToBeDeleted = messageRepository.findById(id);
        if( messageToBeDeleted.isPresent()){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    // Update message text by Id
    public int updateMessageById(int id, Message msg){
        Optional<Message> messageToBeUpdated = messageRepository.findById(id);
        if(msg.getMessageText().length() <= 255 && !msg.getMessageText().isEmpty()){
            if(messageToBeUpdated.isPresent()){
                Message updatedMessage = messageToBeUpdated.get();
                updatedMessage.setMessageText(msg.getMessageText());
                messageRepository.save(updatedMessage);
                return 1;
            }
            return 0;
        }
        return 0;
    }

    // Get all messages by a user
    public List<Message> getAllMessagesByAccountId(int id){
        Optional<Account> targetAccount = accountRepository.findById(id);
        if(targetAccount.isPresent()){
            return messageRepository.findAllMessagesByPostedBy(id);
        }
        return null;
    }

}
