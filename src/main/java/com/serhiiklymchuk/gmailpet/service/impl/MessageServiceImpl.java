package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.MessageException;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.repository.UserRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public
class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;

    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserServiceImpl userService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<MessageDto> getInboxMessages(User user) throws RuntimeException {

        List<Message> messages = messageRepository
                .findAllByReceiverUserIdOrderByDateDesc(user.getId());

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
                .findAllBySenderUserIdOrderByDateDesc(user.getId());

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
    public void createMessage(MessageFormDto messageFormDto, Long senderUserId) {

        User receiverUser = userRepository
                .findByUsername(messageFormDto.getReceiverUsername())
                .orElseThrow(() -> new MessageException("Receiver Was Not Found!!!"));

        Message message = Message.builder()
                .senderUserId(senderUserId)
                .receiverUserId(receiverUser.getId())
                .subject(messageFormDto.getSubject())
                .content(messageFormDto.getContent())
                .reviewed(false)
                .date(LocalDateTime.now())
                .build();

        messageRepository.save(message);
    }

}
