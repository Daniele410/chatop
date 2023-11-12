package com.dan.chatop.service;

import com.dan.chatop.dto.MessageDto;
import com.dan.chatop.model.Message;

public interface IMessageService {
    void sendMessage(MessageDto messageDto);
}