package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Handles HTTP POST requests sent to messages
    // Creates a new message
    public Optional<Message> addNewMessage(Message newMessage) {
        // If newMessage text is not null and text is no longer than 255 characters and the postedBy exists, save the message
        if (newMessage.getMessageText() != null && newMessage.getMessageText().length() <= 255 && messageRepository.existsByPostedBy(newMessage.getPostedBy())) {
            Message savedMessage = messageRepository.save(newMessage);
            // Returns the savedMessage which is set to the newMessage parameter as an optional
            // If it exists return the saved message
            // Else, return an empty optional
            return Optional.of(savedMessage);
        } else {
            return Optional.empty();
        }
        
    }

    // Handles HTTP GET requests sent to /messages
    // Returns all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Handles HTTP GET requests sent to /messages/{messageId}
    // Returns the message with the matching id
    public Optional<Message> getMessageById(Integer messageId) {
        // Searches for the message by id
        Optional<Message> message = messageRepository.findById(messageId);

        // If message was found, return that message
        // Else, return an empty optional because there was no message in the first place
        if (message.isPresent()) {
            return message;
        } else {
            return Optional.empty();
        }
    }

    // Handles HTTP DELETE requests sent to /messages/{messageId}
    // Returns the deleted message
    public Optional<Message> deleteMessageById(Integer messageId) {
        
        // Searches for the message by id 
        Optional<Message> message = messageRepository.findById(messageId);

        // If message was found, delete the message and return that message
        // Else if no message was found, return an empty optional because there was no message in the first place
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            return message;
        } else {
            return Optional.empty();
        }
    }

    // Handles HTTP PATCH requests sent to /message/{messageId}
    // Returns the updated message
    public Optional<Message> patchMessageById(Integer messageId, String messageText) {
        
        // Searches for the message to be patched by id
        Optional<Message> message = messageRepository.findById(messageId);

        // If message was found, update the message and return that message
        // Else if no message was found, return an empty optional because there was no message in the first place
        if (message.isPresent() && messageText != null && messageText.length() <= 255) {
            Message newMessage = message.get();
            newMessage.setMessageText(messageText);
            messageRepository.save(newMessage);
            return Optional.of(newMessage);
        } else {
            return Optional.empty();
        }
    }

    // Handles HTTP GET requests sent to /accounts/{accountId}/messages
    // Returns the list of messages from this account
    public List<Message> getMessagesByAccountId(Integer accountId) {

        // Searches for messages from a specific account
        List<Message> messages = messageRepository.findMessageByPostedBy(accountId);

        return messages;
    }
}
