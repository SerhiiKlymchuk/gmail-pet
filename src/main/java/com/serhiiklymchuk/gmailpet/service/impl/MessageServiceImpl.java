package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getInboxMessages(Long userId) throws RuntimeException {
        return messageRepository
                .findAllByReceiverUserId(userId)
                .orElseThrow(() -> new RuntimeException("Inbox messages not found!"));
    }

    public List<Message> getOutboxMessages(Long userId) throws RuntimeException {
        return messageRepository
                .findAllByOwnerUserId(userId)
                .orElseThrow(() -> new RuntimeException("Outbox messages not found!"));
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }
}
