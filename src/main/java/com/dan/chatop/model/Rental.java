package com.dan.chatop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private Double surface;

    private Double price;

    private String picture;

    private String description;

    @JsonProperty("owner_id")
    @Column(name = "owner_id")
    private Long ownerId;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt= LocalDateTime.now();

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt= LocalDateTime.now();

}
