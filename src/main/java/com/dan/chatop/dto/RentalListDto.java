package com.dan.chatop.dto;

import com.dan.chatop.model.Rental;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalListDto {

    private List<Rental> rentals;

}
