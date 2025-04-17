package com.example.service;

import java.util.ArrayList;
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

    // Handles HTTP GET requests sent to /messages
    // Returns all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Handles HTTP GET requests sent to /messages/{messageId}
    // Returns the message with the matching id
    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    // Handles HTTP DELETE requests sent to /messages/{messageId}
    // Returns the deleted message
    public Optional<Message> deleteMessageById(Integer messageId) {
        
        // Searches for the message by id 
        Optional<Message> message = messageRepository.findById(messageId);

        // If message was found, delete the message and return that message
        // Else if no message was found, return an empty optional because there is no message in the first place
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            return message;
        } else {
            return Optional.empty();
        }
    }

    // Handles HTTP PATCH requests sent to /message/{messageId}
    // Returns the updated message
    public Optional<Message> updateMessageById(Integer messageId) {
        
    }
}
