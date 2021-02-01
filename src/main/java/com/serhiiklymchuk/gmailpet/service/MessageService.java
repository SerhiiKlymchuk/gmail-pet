package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;

import java.util.List;

public interface MessageService {

    void createMessage(MessageFormDto messageFormDto, Long senderUserId);

    List<MessageDto> getInboxMessages(User user);

    List<MessageDto> getOutboxMessages(User user);

}
