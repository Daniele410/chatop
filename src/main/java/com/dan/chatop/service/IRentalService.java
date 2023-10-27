package com.dan.chatop.service;

import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.model.Rental;

import java.io.IOException;
import java.util.List;

public interface IRentalService{
    List<Rental> getAllRentals();

    Rental getRentalByUserId(Long id);

//    Rental addRental(Rental rental);

    Rental addRental(RentalDto rentalDto) throws IOException;

    boolean existsById(Long id);

    Rental updateRental(Rental rental);

    void deleteRental(Long id);
}
