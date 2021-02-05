package com.serhiiklymchuk.gmailpet.service.impl;

import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.service.SendMessageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMessageImpl implements SendMessageService {

    private final MessageService messageService;

    public SendMessageImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    @Async
    public void sendMessage(MessageFormDto messageFormDto, Long senderUserId) {

        String[] users = messageFormDto
                .getReceiverUsername()
                .split(",");

        for (String user : users) {
            MessageFormDto sendMessageFormDto = messageFormDto.toBuilder()
                    .receiverUsername(user)
                    .build();

            messageService.createMessage(sendMessageFormDto, senderUserId);
        }
    }

}
