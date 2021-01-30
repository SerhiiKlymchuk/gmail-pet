package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.MessageException;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.repository.UserRepository;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.util.mapper.MessageToMessageDtoMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Long GMAIL_SUPPORT_ID = 1L;

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

    @Async
    @Override
    public void createMessage(MessageFormDto messageFormDto, Long senderUserId) {

        try {
            User receiverUser = userRepository
                    .findByUsername(messageFormDto.getReceiverUsername())
                    .orElseThrow(() -> new MessageException("Message wasn't delivered!!!"));

            Message message = Message.builder()
                    .senderUserId(senderUserId)
                    .receiverUserId(receiverUser.getId())
                    .subject(messageFormDto.getSubject())
                    .content(messageFormDto.getContent())
                    .reviewed(false)
                    .date(LocalDateTime.now())
                    .build();

            messageRepository.save(message);

        } catch (MessageException e) {
            Message message = Message.builder()
                    .senderUserId(GMAIL_SUPPORT_ID)
                    .receiverUserId(senderUserId)
                    .subject("Your message wasn't delivered!")
                    .content("Hey, user with username `"
                            + messageFormDto.getReceiverUsername()
                            + "` was not found! And message wasn't delivered!")
                    .reviewed(false)
                    .date(LocalDateTime.now())
                    .build();

            messageRepository.save(message);
        }

    }

}
