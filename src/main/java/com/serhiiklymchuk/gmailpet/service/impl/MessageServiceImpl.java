package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.SendMessageException;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.repository.UserRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.util.mapper.MessageToMessageDtoMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final MessageToMessageDtoMapper messageToMessageDtoMapper;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, MessageToMessageDtoMapper messageToMessageDtoMapper) {

        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageToMessageDtoMapper = messageToMessageDtoMapper;
    }

    @Override
    public List<MessageDto> getInboxMessages(User user) {

        List<Message> messages = messageRepository
                .findAllByReceiverUserIdOrderByDateDesc(user.getId());

        return messageToMessageDtoMapper.map(messages);
    }

    @Override
    public List<MessageDto> getOutboxMessages(User user) {

        List<Message> messages = messageRepository
                .findAllBySenderUserIdOrderByDateDesc(user.getId());

        return messageToMessageDtoMapper.map(messages);
    }

    @Override
    public List<MessageDto> searchInboxMessages(User user, String searchQuery) {
        List<MessageDto> inboxMessages = getInboxMessages(user);

        return inboxMessages.stream()
                .filter(e -> e
                        .getSubject().toLowerCase()
                        .contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> searchOutboxMessages(User user, String searchQuery) {
        List<MessageDto> outboxMessages = getOutboxMessages(user);

        return outboxMessages.stream()
                .filter(e -> e
                        .getSubject().toLowerCase()
                        .contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Async
    @Override
    public void createMessage(MessageFormDto messageFormDto, Long senderUserId) {

        User receiverUser = userRepository
                .findByUsername(messageFormDto.getReceiverUsername())
                .orElseThrow(() -> new SendMessageException("Message was not sent!!!"));

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
