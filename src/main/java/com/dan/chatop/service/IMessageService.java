package com.dan.chatop.service;

import com.dan.chatop.model.Message;

public interface IMessageService {
    void postMessage(Message message);

    Message getMessageById(Long id);

    void deleteMessage(Long id);

    void updateMessageById(Long id, Message message);
}
