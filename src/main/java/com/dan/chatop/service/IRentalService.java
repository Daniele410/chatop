package com.dan.chatop.service;

import com.dan.chatop.dto.MessageResponse;
import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.dto.RentalRequestDto;
import com.dan.chatop.dto.RentalSimple;
import com.dan.chatop.model.Rental;

import java.util.List;

public interface IRentalService{
    List<Rental> getAllRentals();

    RentalSimple getRentalById(Long id);

//    Rental addRental(Rental rental);

//    Rental addRental(RentalDto rentalDto) ;

    MessageResponse createRental(RentalDto rentalDto);

    boolean existsById(Long id);

    MessageResponse updateRental(Long id, RentalRequestDto rentalRequestDto);

    void deleteRental(Long id);
}
