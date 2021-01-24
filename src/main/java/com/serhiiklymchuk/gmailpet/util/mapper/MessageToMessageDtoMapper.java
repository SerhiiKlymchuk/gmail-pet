package com.serhiiklymchuk.gmailpet.util.mapper;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageToMessageDtoMapper {

    private final UserRepository userRepository;

    public MessageToMessageDtoMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private MessageDto map(Message message, User receiverUser, User senderUser) {

        return MessageDto.builder()
                .receiverUsername(receiverUser.getUsername())
                .senderUsername(senderUser.getUsername())
                .subject(message.getSubject())
                .content(message.getContent())
                .reviewed(message.isReviewed())
                .date(message.getDate())
                .build();
    }

    public List<MessageDto> map(List<Message> messages, User user) {

        return messages.stream()
                .map(m -> {
                    if (user.getId().equals(m.getReceiverUserId())) {
                        return map(m, user, m.getSenderUserId());
                    }
                    return map(m, m.getReceiverUserId(), user);
                })
                .collect(Collectors.toList());
    }

    private MessageDto map(Message message, User receiverUser, Long senderUserId) {

        User senderUser = userRepository
                .findById(senderUserId)
                .orElseThrow(() -> new RuntimeException("User was not found!!"));

        return map(message, receiverUser, senderUser);
    }

    private MessageDto map(Message message, Long receiverUserId, User senderUser) {

        User receiverUser = userRepository
                .findById(receiverUserId)
                .orElseThrow(() -> new RuntimeException("User was not found!!"));

        return map(message, receiverUser, senderUser);
    }

}
