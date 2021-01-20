package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserServiceImpl userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public List<MessageDto> getInboxMessages(User user) throws RuntimeException {

        List<Message> messages = messageRepository
                .findAllByReceiverUserIdOrderByDateDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("Inbox messages not found!"));

        return messages.stream().map(msg -> MessageDto.builder()
                .receiverUsername(userService.findById(msg.getReceiverUserId()).getUsername())
                .senderUsername(userService.findById(msg.getSenderUserId()).getUsername())
                .subject(msg.getSubject())
                .content(msg.getContent())
                .reviewed(msg.isReviewed())
                .date(msg.getDate())
                .build()).collect(Collectors.toList());
    }

    public List<MessageDto> getOutboxMessages(User user) throws RuntimeException {

        List<Message> messages = messageRepository
                .findAllBySenderUserIdOrderByDateDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("Outbox messages not found!"));

        return messages.stream().map(msg -> MessageDto.builder()
                .receiverUsername(userService.findById(msg.getReceiverUserId()).getUsername())
                .senderUsername(userService.findById(msg.getSenderUserId()).getUsername())
                .subject(msg.getSubject())
                .content(msg.getContent())
                .reviewed(msg.isReviewed())
                .date(msg.getDate())
                .build()).collect(Collectors.toList());
    }

    @Override
    public boolean createMessage(MessageFormDto messageFormDto, Long senderUserId) {
        User receiverUser = null;

        try {
            receiverUser = userService.loadUserByUsername(messageFormDto.getReceiverUsername());
        } catch (UsernameNotFoundException e) {
            return false;
        }

        Message message = Message.builder()
                .senderUserId(senderUserId)
                .receiverUserId(receiverUser.getId())
                .subject(messageFormDto.getSubject())
                .content(messageFormDto.getContent())
                .reviewed(false)
                .date(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        return true;
    }

}
