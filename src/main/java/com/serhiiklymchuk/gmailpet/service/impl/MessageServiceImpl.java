package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.MessageStatus;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.MessageException;
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
                .findAllByReceiverUserIdAndReceiveStatus(
                        user.getId(), MessageStatus.RECEIVED, pageable);

        return new PageImpl<>(
                messageToMessageDtoMapper.map(messages), pageable,
                messageRepository.countAllByReceiverUserIdAndReceiveStatus(user.getId(), MessageStatus.RECEIVED)
        );
    }

    @Override
    public Page<MessageDto> getOutboxMessages(User user, Pageable pageable) {

        List<Message> messages = messageRepository
                .findAllBySenderUserIdAndSendStatus(
                        user.getId(), MessageStatus.SENT, pageable);

        return new PageImpl<>(
                messageToMessageDtoMapper.map(messages), pageable,
                messageRepository.countAllBySenderUserIdAndSendStatus(user.getId(), MessageStatus.SENT)
        );
    }

    @Override
    public Page<MessageDto> getRecycleBinMessages(User user, Pageable pageable) {
        List<Message> messages = messageRepository
                .findAllBySenderUserIdAndSendStatusOrReceiverUserIdAndReceiveStatus(
                        user.getId(), MessageStatus.ON_RECYCLE, user.getId(), MessageStatus.ON_RECYCLE, pageable
                );


        return new PageImpl<>(
                messageToMessageDtoMapper.map(messages), pageable,
                messageRepository.countAllBySenderUserIdAndSendStatusOrReceiverUserIdAndReceiveStatus(
                        user.getId(), MessageStatus.ON_RECYCLE, user.getId(), MessageStatus.ON_RECYCLE)
        );
    }

    @Override
    public Page<MessageDto> searchInboxMessages(User user, String searchQuery, Pageable pageable) {

        List<MessageDto> inboxMessages = messageToMessageDtoMapper.map(
                messageRepository.findAllByReceiverUserIdAndSubjectContainsAndReceiveStatus(
                        user.getId(), searchQuery, MessageStatus.RECEIVED, pageable)
        );

        return new PageImpl<>(inboxMessages, pageable,
                messageRepository.countAllByReceiverUserIdAndSubjectContainsAndReceiveStatus(
                        user.getId(), searchQuery, MessageStatus.RECEIVED)
        );
    }

    @Override
    public Page<MessageDto> searchOutboxMessages(User user, String searchQuery, Pageable pageable) {

        List<MessageDto> inboxMessages = messageToMessageDtoMapper.map(
                messageRepository.findAllBySenderUserIdAndSubjectContainsAndSendStatus(
                        user.getId(), searchQuery, MessageStatus.SENT, pageable)
        );

        return new PageImpl<>(inboxMessages, pageable,
                messageRepository.countAllBySenderUserIdAndSubjectContainsAndSendStatus(
                        user.getId(), searchQuery, MessageStatus.SENT)
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
                .receiveStatus(MessageStatus.RECEIVED)
                .sendStatus(MessageStatus.SENT)
                .reviewed(false)
                .date(LocalDateTime.now())
                .build();

        messageRepository.save(message);
    }

    @Override
    public MessageDto findById(User user, Long messageId) {

        List<MessageDto> message = messageToMessageDtoMapper.map(List.of(
                messageRepository
                        .findById(messageId)
                        .orElseThrow(() -> new MessageException("Message was not found!")))
        );

        return message.get(0);
    }


    @Override
    public void recycleMessage(User user, MessageDto message) {

        if (user.getUsername().equals(message.getReceiverUsername())) {
            messageRepository.updateReceiveStatus(MessageStatus.ON_RECYCLE, message.getId());
        } else if (user.getUsername().equals(message.getSenderUsername())) {
            messageRepository.updateSendStatus(MessageStatus.ON_RECYCLE, message.getId());
        }
    }

}
