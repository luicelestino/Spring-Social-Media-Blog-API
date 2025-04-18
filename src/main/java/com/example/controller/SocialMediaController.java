package com.example.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
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

    private AccountRepository accountRepository;

    public SocialMediaController(AccountService accountService, MessageService messageService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
    }

    // Create a new account
    @PostMapping("register")
    public ResponseEntity<?> registerAccount(@RequestBody Account newAccount) {
        Optional<Account> registeredAccount = accountService.registerAccount(newAccount);

        // If account is present, return JSON of account with a status of 200
        // If account can't be registered due to duplicate, return status 209
        // If account can't be registered for any other reason, return 400

        if (registeredAccount.isPresent()) {
            return ResponseEntity.ok(registeredAccount.get());
        } else if (accountRepository.existsByUsername(newAccount.getUsername())){
            return ResponseEntity.status(409).body("Username already exists");
        } else {
            return ResponseEntity.badRequest().body("Account could not be registered");
        }
    }
 
    // Login to an existing account
    @PostMapping("login")
    public ResponseEntity<?> loginAccount(@RequestBody Account loginAccount) {
        Optional<Account> account = accountService.loginAccount(loginAccount);

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Create a new message
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        Optional<Message> savedMessage = messageService.addNewMessage(newMessage);

        // If the message is present, return the saved message with a status of 200
        // Else, return a null body response and a status of 400
        if (savedMessage.isPresent()) {
            return ResponseEntity.ok(savedMessage.get());
        } else {
            return ResponseEntity.status(400).body(null);
        }
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

        // Return the response body with a status of 200 if message is present
        // Return an empty response body if message is null
        return ResponseEntity.ok(messageById.orElse(null));
    }
    
    // Delete a message by its ID
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        Optional<Message> deletedMessage = messageService.deleteMessageById(messageId);

        if (deletedMessage.isPresent()) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    // Patch a message by its ID
    // Because the JSON object is not guaranteed to only contain the new message text, we map it to a Map object
    // We then extract the messageText value from the Map to get the new message text from the request body
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> patcheMessageById(@PathVariable Integer messageId, @RequestBody Map<String, Object> requestBody) {

        // Get the messageText value from the JSON request body
        // Cast it to string
        String messageNewText = (String) requestBody.get("messageText");

        Optional<Message> patchedMessage = messageService.patchMessageById(messageId, messageNewText);

        if (patchedMessage.isPresent()) {
            return ResponseEntity.ok().body(1);
        } else {
            return ResponseEntity.badRequest().body("Update was unsuccessful");
        }
    }

    // Get all messages written by a specific account by its ID
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccount(@PathVariable Integer accountId) {
        List<Message> accountMessages = messageService.getMessagesByAccountId(accountId);

        return ResponseEntity.ok(accountMessages);
    }

}
// Commit