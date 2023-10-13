package com.dan.chatop.service;

import com.dan.chatop.model.Rental;
import com.dan.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements IRentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).get();
    }

    @Override
    public Rental addRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}
