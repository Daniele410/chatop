package com.dan.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
}
