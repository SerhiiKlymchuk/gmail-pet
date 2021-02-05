package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;

public interface SendMessageService {

    void sendMessage(MessageFormDto messageFormDto, Long senderUserId);
}
