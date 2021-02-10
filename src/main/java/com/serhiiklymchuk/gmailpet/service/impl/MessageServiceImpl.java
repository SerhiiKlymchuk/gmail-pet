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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public Page<MessageDto> getInboxMessages(User user, Pageable pageable) {

        List<Message> messages = messageRepository
                .findAllByReceiverUserIdOrderByDateDesc(user.getId(), pageable);

        return new PageImpl<>(
                messageToMessageDtoMapper.map(messages), pageable,
                messageRepository.countAllByReceiverUserId(user.getId())
        );
    }

    @Override
    public Page<MessageDto> getOutboxMessages(User user, Pageable pageable) {

        List<Message> messages = messageRepository
                .findAllBySenderUserIdOrderByDateDesc(user.getId(), pageable);

        return new PageImpl<>(
                messageToMessageDtoMapper.map(messages), pageable,
                messageRepository.countAllBySenderUserId(user.getId())
        );
    }

    @Override
    public Page<MessageDto> searchInboxMessages(User user, String searchQuery, Pageable pageable) {

        List<MessageDto> inboxMessages = messageToMessageDtoMapper.map(
                messageRepository.findAllByReceiverUserIdAndSubjectContains(user.getId(), searchQuery, pageable)
        );

        return new PageImpl<>(inboxMessages, pageable,
                messageRepository.countAllByReceiverUserIdAndSubjectContains(user.getId(), searchQuery)
        );
    }

    @Override
    public Page<MessageDto> searchOutboxMessages(User user, String searchQuery, Pageable pageable) {

        List<MessageDto> inboxMessages = messageToMessageDtoMapper.map(
                messageRepository.findAllBySenderUserIdAndSubjectContains(user.getId(), searchQuery, pageable)
        );

        return new PageImpl<>(inboxMessages, pageable,
                messageRepository.countAllBySenderUserIdAndSubjectContains(user.getId(), searchQuery)
        );
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
