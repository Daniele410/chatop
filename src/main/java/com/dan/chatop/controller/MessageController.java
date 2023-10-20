package com.dan.chatop.controller;

import com.dan.chatop.model.Message;
import com.dan.chatop.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/messages/")
class MessageController {

    @Autowired
    IMessageService messageService;

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        messageService.postMessage(message);
        return ResponseEntity.ok(message);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> getMessageById(@RequestBody Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

}
