package com.dan.chatop.service;

import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.model.Message;
import com.dan.chatop.model.Rental;
import com.dan.chatop.model.User;
import com.dan.chatop.repository.MessageRepository;
import com.dan.chatop.repository.RentalRepository;
import com.dan.chatop.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MessageServiceImpl implements IMessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final RentalRepository rentalRepository;

    @Override
    public void postMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void updateMessageById(Long id, Message message) {
        Message messageToUpdate = messageRepository.findById(id).get();
        messageToUpdate.setMessage(message.getMessage());
        messageRepository.save(messageToUpdate);
    }

    @Override
    public void sendMessage(MessageDto messageDto) {
        Optional<User> user = userRepository.findById(messageDto.getUser_id());
        Optional<Rental> rental = rentalRepository.findById(messageDto.getRental_id());
        Message message = new Message();
        message.setMessage(messageDto.getMessage());
        message.setUser(user.get());
        message.setRental(rental.get());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(message);
        log.info("Send message successfully");
    }

}