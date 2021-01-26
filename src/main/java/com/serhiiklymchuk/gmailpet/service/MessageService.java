package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;

public interface MessageService {

    void createMessage(MessageFormDto messageFormDto, Long senderUserId);

}
