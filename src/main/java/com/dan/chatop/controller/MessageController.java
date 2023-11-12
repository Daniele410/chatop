package com.dan.chatop.controller;

import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.dto.MessageResponse;
import com.dan.chatop.dto.RentalListDto;
import com.dan.chatop.exception.RentalNotFondException;
import com.dan.chatop.exception.ResourceNotFoundException;
import com.dan.chatop.service.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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

    @Operation(summary = "Send a message to Rental owner", description = "To send a message required : userId, rentalId and message",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Message send with success", content = { @Content(mediaType = "application/json"
                            , schema = @Schema(implementation = MessageResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "Message send with success"
                                    }"""))

                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "type": "https://httpstatuses.com/400",
                                        "title": "Bad Request",
                                        "status": 400,
                                        "detail": "Verify your input Data"
                                    }"""))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "type": "https://httpstatuses.com/401",
                                        "title": "Unauthorized",
                                        "status": 401,
                                        "detail": "You should be authenticated to access this resource"
                                    }"""))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class),
                                    examples = @ExampleObject(value = """
                                    {
                                        "type": "https://httpstatuses.com/403",
                                        "title": "Forbidden",
                                        "status": 403,
                                        "detail": "Access Denied"
                                    }"""))
                    })
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Message to send", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class),
                    examples = @ExampleObject(value = """
                                    {
                                        "owner_id": 1,
                                        "rental_id": 1,
                                        "message": "Hello, your rental is very nice, i want to rent it !"
                                    }""")))

    @Parameter(name = "Authorization", description = "JWT Bearer token", in = ParameterIn.HEADER, required = true,
            schema = @Schema(type = "string", format = "Bearer your_jwt_token_here"),
            example = "Bearer your_jwt_token_here")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageDto message) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (userDetails == null) {
            return ResponseEntity.status(FORBIDDEN).build();
        }

        if (message.getMessage() == null || message.getRental_id() == null || message.getUser_id() == null ) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }
        messageService.sendMessage(message);
        MessageResponse messageResponse = new MessageResponse("Message send with success");
        return new ResponseEntity<>(messageResponse, CREATED);
    }

}
