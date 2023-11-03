package com.dan.chatop.service;

import com.dan.chatop.dto.RentalDto;
import com.dan.chatop.dto.RentalRequestDto;
import com.dan.chatop.exception.InvalidImageFormatException;
import com.dan.chatop.model.Rental;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.RentalRepository;
import com.dan.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements IRentalService {

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.upload-url}")
    private String uploadUrl;

    @Override
    @SneakyThrows
    public List<Rental> getAllRentals() {
        log.info("Rentals retrieved successfully");
        return rentalRepository.findAll();
    }

    @Override
    public RentalRequestDto getRentalByUserId(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental with ID " + rentalId + " not found"));
        RentalRequestDto rentalRequestDto = RentalRequestDto.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
        log.info("Rental retrieved successfully");
        return rentalRequestDto;
    }

    @Override
    public Rental createRental(RentalDto rentalDto) {

        String pictureLocation = uploadDir + "/" + savePicture(rentalDto.getPicture());

        final RentalRequestDto rental = getRentalRequestDTO(rentalDto, pictureLocation);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database!"));

        Rental rentalSave = new Rental();
        rentalSave.setName(rental.getName());
        rentalSave.setSurface(rental.getSurface());
        rentalSave.setPrice(rental.getPrice());
        rentalSave.setDescription(rental.getDescription());
        rentalSave.setPicture(rental.getPicture());
        rentalSave.setOwnerId(authUser.getId());
        rentalSave.setCreatedAt(LocalDateTime.now());
        rentalSave.setUpdatedAt(LocalDateTime.now());
        rentalRepository.save(rentalSave);
        log.info("Rental created successfully");
        return rentalSave;
    }

    @Override
    public boolean existsById(Long id) {
        return rentalRepository.existsById(id);
    }

    @Override
    public RentalRequestDto updateRental(Long id,RentalRequestDto rentalRequestDto) {
        Rental rental =rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental with ID " + id + " not found"));
        setRental(rentalRequestDto, rental);
        rentalRepository.save(rental);
        return rentalRequestDto;
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    private static RentalRequestDto getRentalRequestDTO(RentalDto rentalRequest, String pictureLocation) {
        RentalRequestDto rentalRequestDto = RentalRequestDto.builder()
                .name(rentalRequest.getName())
                .surface(rentalRequest.getSurface())
                .price(rentalRequest.getPrice())
                .picture(pictureLocation)
                .description(rentalRequest.getDescription())
                .build();
        rentalRequestDto.setPicture(pictureLocation);
        return rentalRequestDto;
    }

    @SneakyThrows
    private String savePicture(MultipartFile image) {
        String contentType = image.getContentType();

        if (contentType == null) {
            log.error("The format type of the image is unknown.");
            throw new InvalidImageFormatException("The format type of the image is unknown.");
        }
        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"))) {
            log.error("Only images in JPG, PNG or JPEG format are accepted.");
            throw new InvalidImageFormatException("Invalid format, only images in JPG, PNG or JPEG  are accepted.");
        }
        try {
            Path path = Paths.get(uploadUrl + uploadDir, image.getOriginalFilename());
            Files.write(path, image.getBytes());
            log.info("Rental Image saved successfully");
            return image.getOriginalFilename();
        } catch (IOException e) {
            log.error("Error while saving rental image");
            throw e;
        }
    }

    private static void setRental(RentalRequestDto rentalRequest, Rental rental) {
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());
    }

}
