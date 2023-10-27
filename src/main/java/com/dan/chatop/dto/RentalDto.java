package com.dan.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Long id;
    private String name;
    private long surface;
    private long price;
    private MultipartFile picture;
    private String description;
    private Integer ownerId;
    private Date createdAt;
    private Date updatedAt;
}
