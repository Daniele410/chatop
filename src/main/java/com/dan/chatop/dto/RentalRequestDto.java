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
    private String name;
    private Double surface;
    private Double price;
    private String description;
}
