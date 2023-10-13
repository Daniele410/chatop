package com.dan.chatop.service;

import com.dan.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRentalService{
    List<Rental> getAllRentals();

    Rental getRentalById(Long id);

    Rental addRental(Rental rental);
}
