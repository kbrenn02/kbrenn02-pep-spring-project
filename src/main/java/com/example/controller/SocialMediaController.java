package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account acct){
        Account loggedInAccount = accountService.loginUser(acct);
        if(loggedInAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(loggedInAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message msg){
        Message postedMsg = messageService.postMessage(msg);
        if(postedMsg == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(postedMsg);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> msgs = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgs);
    }
}
