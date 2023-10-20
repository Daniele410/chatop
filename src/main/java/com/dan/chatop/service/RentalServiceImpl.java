package com.dan.chatop.service;

import com.dan.chatop.model.Rental;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.RentalRepository;
import com.dan.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements IRentalService {

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental getRentalByUserId(Long rentalId) {
        User user = userRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental with ID " + rentalId + " not found"));
        return rentalRepository.findById(user.getId()).get();
    }


    @Override
    public Rental addRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public boolean existsById(Long id) {
        return rentalRepository.existsById(id);
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}
