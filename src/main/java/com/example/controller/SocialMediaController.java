package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final MessageService messageService;
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    // Register a new users
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account acct){
        if(accountService.checkUser(acct) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        Account registeredAccount = accountService.registerUser(acct);
        
        if(registeredAccount == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        }
    }

    // Login an existing user
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account acct){
        Account loggedInAccount = accountService.loginUser(acct);
        if(loggedInAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(loggedInAccount);
        }
    }

    // Create a new message
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message msg){
        Message postedMsg = messageService.postMessage(msg);
        if(postedMsg == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(postedMsg);
        }
    }

    // Get a list of all the messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> msgs = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgs);
    }

    // Get a specific message by its ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message msg = messageService.getMessageById(messageId);
        if(msg == null){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    // Detele a specific message by its ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int numRowsUpdated = messageService.deleteMessageById(messageId);
        if(numRowsUpdated > 0){
            return ResponseEntity.status(HttpStatus.OK).body(numRowsUpdated);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    // Update message text by its ID
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message msg){
        int numRowsUpdated = messageService.updateMessageById(messageId, msg);
        if(numRowsUpdated > 0){
            return ResponseEntity.status(HttpStatus.OK).body(numRowsUpdated);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Get a list of all messages for a specific user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId){
        List<Message> msgs = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(msgs);
    }
}
