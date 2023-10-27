package com.dan.chatop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long surface;

    private long price;

    private byte[] picture;

    private String description;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "created_at")
    private Date createdAt= Date.from(java.time.Instant.now());

    @Column(name = "updated_at")
    private Date updatedAt= Date.from(java.time.Instant.now());

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;


}
