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
                .id(message.getId())
                .receiverUsername(receiverUser.getUsername())
                .senderUsername(senderUser.getUsername())
                .subject(message.getSubject())
                .content(message.getContent())
                .receiveStatus(message.getReceiveStatus())
                .sendStatus(message.getSendStatus())
                .reviewed(message.isReviewed())
                .date(message.getDate())
                .build();
    }

    public List<MessageDto> map(List<Message> messages) {
        List<User> users = (List<User>) userRepository.findAll();

        return messages.stream()
                .map(msg -> {

                    User receiverUser = users.stream()
                            .filter(u -> u
                                    .getId()
                                    .equals(msg.getReceiverUserId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("User not found!"));

                    User senderUser = users.stream()
                            .filter(u -> u
                                    .getId()
                                    .equals(msg.getSenderUserId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("User not found!"));

                    return map(msg, receiverUser, senderUser);
                })
                .collect(Collectors.toList());
    }

}
