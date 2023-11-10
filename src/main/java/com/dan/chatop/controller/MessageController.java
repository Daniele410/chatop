package com.dan.chatop.controller;

import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.dto.MessageResponse;
import com.dan.chatop.service.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
class MessageController {

    private final IMessageService messageService;

    @Operation(summary = "Send Message", description = "Send a message.")
    @Parameter(name = "Authorization", description = "JWT Bearer token", in = ParameterIn.HEADER, required = true,
            schema = @Schema(type = "string", format = "Bearer your_jwt_token_here"),
            example = "Bearer your_jwt_token_here")
    @SecurityRequirement(name = "bearerAuth")
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
