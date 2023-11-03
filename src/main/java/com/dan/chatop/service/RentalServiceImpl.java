package com.dan.chatop.service;

import com.dan.chatop.auth.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.upload-url}")
    private String uploadUrl;

    @Override
    @SneakyThrows
    public List<Rental> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        log.info("Rentals retrieved successfully");
        return rentalRepository.findAll();
    }

//    @Override
//    public List<Rental> getAllRentals() {
//
//        return rentalRepository.findAll();
//    }


    @Override
    public RentalRequestDto getRentalByUserId(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental with ID " + rentalId + " not found"));
        RentalRequestDto rentalRequestDto = RentalRequestDto.builder()
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .build();
        log.info("Rental retrieved successfully");
        return rentalRequestDto;
    }


    //    @Override
//    public Rental addRental(RentalDto rentalDto) throws IOException {
//        String userEmail = authenticationService.getAuthenticatedUserEmail();
//        Optional<User> user = userRepository.findByEmail(userEmail);
//        Rental rental = new Rental();
//        rental.setName(rentalDto.getName());
//        rental.setSurface(rentalDto.getSurface());
//        rental.setPrice(rentalDto.getPrice());
//        rental.setDescription(rentalDto.getDescription());
//        rental.setOwnerId(user.get().getId());
//        rental.setCreatedAt(rental.getCreatedAt());
//        rental.setUpdatedAt(rental.getUpdatedAt());
//        MultipartFile file = rentalDto.getPicture();
//        byte[] bytes = file.getBytes();
//
//
//        Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
//        Files.write(path.getFileName(), bytes);
//        rental.setPicture(file.getOriginalFilename().getBytes());
//        return rentalRepository.save(rental);
//    }
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
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }


    private static void setRental(RentalDto rentalRequest, Rental rental) {
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());
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

}
