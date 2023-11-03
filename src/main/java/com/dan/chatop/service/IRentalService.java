package com.dan.chatop.service;

import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.dto.RentalRequestDto;
import com.dan.chatop.model.Rental;

import java.util.List;

public interface IRentalService{
    List<Rental> getAllRentals();

    RentalRequestDto getRentalByUserId(Long id);

//    Rental addRental(Rental rental);

//    Rental addRental(RentalDto rentalDto) ;

    Rental createRental(RentalDto rentalDto);

    boolean existsById(Long id);

    Rental updateRental(Rental rental);

    void deleteRental(Long id);
}
