package com.dan.chatop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {
    private Long user_id;
    private Long rental_id;
    private String message;
}
