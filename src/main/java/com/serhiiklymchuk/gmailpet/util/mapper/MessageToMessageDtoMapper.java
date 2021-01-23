package com.serhiiklymchuk.gmailpet.util.mapper;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import com.serhiiklymchuk.gmailpet.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageToMessageDtoMapper {

    private final UserRepository userRepository;

    public MessageToMessageDtoMapper(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
    }

    public List<MessageDto> map(List<Message> messages){
        return messages.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public MessageDto map(Message message){

        User receiverUser = userRepository
                .findById(message.getReceiverUserId())
                .orElseThrow(() -> new RuntimeException("User was not found!!"));

        User senderUser = userRepository
                .findById(message.getSenderUserId())
                .orElseThrow(() -> new RuntimeException("User was not found!!"));

        return MessageDto.builder()
                .receiverUsername(receiverUser.getUsername())
                .senderUsername(senderUser.getUsername())
                .subject(message.getSubject())
                .content(message.getContent())
                .reviewed(message.isReviewed())
                .date(message.getDate())
                .build();
    }
}
