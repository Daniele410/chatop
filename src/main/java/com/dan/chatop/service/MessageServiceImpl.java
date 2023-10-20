package com.dan.chatop.service;

import com.dan.chatop.model.Message;
import com.dan.chatop.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

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

}
