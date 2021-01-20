package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserServiceImpl userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public List<Message> getInboxMessages(String username) throws RuntimeException {

        User user = userService.loadUserByUsername(username);

        return messageRepository
                .findAllByReceiverUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Inbox messages not found!"));
    }

    public List<Message> getOutboxMessages(String username) throws RuntimeException {

        User user = userService.loadUserByUsername(username);

        return messageRepository
                .findAllByOwnerUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Outbox messages not found!"));
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }
}
