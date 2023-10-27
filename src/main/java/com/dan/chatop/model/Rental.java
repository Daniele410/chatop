package com.dan.chatop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.Instant;
import java.time.LocalDate;
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

    @JsonProperty("owner_id")
    @Column(name = "owner_id")
    private Long ownerId;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDate createdAt= LocalDate.now();

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDate updatedAt= LocalDate.now();

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;


}
