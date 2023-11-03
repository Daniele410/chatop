package com.dan.chatop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long rental_id;
    private Long user_id;
    private String message;
}
