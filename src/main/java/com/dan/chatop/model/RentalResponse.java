package com.dan.chatop.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalResponse {

    private List<Rental> rentals;

}
