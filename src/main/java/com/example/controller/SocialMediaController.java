package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azul.crs.client.Response;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    private List<Message> messageList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();

    // Create a new account
    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {

    }

    // Login to an existing account
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account loginAccount) {

    }

    // Create a new message
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {

    }

    // Get all existing messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        // Create a list of messages that we populate using messageService 
        List<Message> allMessages = messageService.getAllMessages();
        // Return the ResponseEntity and list with a status code of 200 (OK)
        return ResponseEntity.ok(allMessages);

    }

    // Retrieve a message by its ID
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        // Search for the message by id and set it to messageById
        Optional<Message> messageById = messageService.getMessageById(messageId);

        // If the message is null i.e does not exist, return status code 404
        if (!messageById.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Otherwise, return messageById
        return ResponseEntity.ok(messageById.get());
    }

    // Delete a message by its ID
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Message> deleteMessageById(@PathVariable Integer messageId) {

    }

    // Patch a message by its ID
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Message> patcheMessageById(@PathVariable Integer messageId, @RequestBody String messageText) {

    }

    // Get all messages written by a specific account by its ID
    @GetMapping("accounts/{accountId}")
    public ResponseEntity<Message> getAllMessagesByAccount(@PathVariable Integer accountId) {
        
    }

}
