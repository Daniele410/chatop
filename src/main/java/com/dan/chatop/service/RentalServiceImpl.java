package com.dan.chatop.service;

import com.dan.chatop.auth.AuthenticationService;
import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.model.Rental;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.RentalRepository;
import com.dan.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements IRentalService {

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    public Rental addRental(RentalDto rentalDto) throws IOException {
        String userEmail = authenticationService.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(userEmail);
        Rental rental = new Rental();
        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());
        rental.setOwnerId(user.get().getId());
        rental.setCreatedAt(rental.getCreatedAt());
        rental.setUpdatedAt(rental.getUpdatedAt());
        MultipartFile file = rentalDto.getPicture();
        byte[] bytes = file.getBytes();

        Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
        rental.setPicture(file.getOriginalFilename().getBytes());
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
