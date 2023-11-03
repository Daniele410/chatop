package com.dan.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequestDto {
    private Long id;
    private String name;
    private Long surface;
    private Long price;
    private String picture;
    private String description;
}
