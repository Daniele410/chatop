package com.dan.chatop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Data
@Table(name = "Rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*
    @Column(length = 255)
*/
/*
    @NotBlank(message = "name is mandatory")
*/
    private String name;
/*
    @NotNull(message = "surface is mandatory")
*/
    private long surface;
/*
    @NotNull(message = "price is mandatory")
*/
    private long price;

    private String picture;

/*
    @NotBlank(message = "description is mandatory")
*/
/*
    @Column(length = 2000)
*/
    private String description;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "created_at")
    private Date createdAt= Date.from(java.time.Instant.now());

    @Column(name = "updated_at")
    private Date updatedAt= Date.from(java.time.Instant.now());

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;

    public Rental() {
    }

}
