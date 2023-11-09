package com.dan.chatop.controller;

import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.dto.MessageResponse;
import com.dan.chatop.service.IMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
class MessageController {

    private final IMessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageDto message) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (userDetails == null) {
            return ResponseEntity.badRequest().build();
        }
        messageService.sendMessage(message);
        MessageResponse messageResponse = new MessageResponse("Message send with success");
        return ResponseEntity.ok(messageResponse);
    }

}
