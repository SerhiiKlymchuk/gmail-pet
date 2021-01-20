package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;

public interface MessageService {

    boolean createMessage(MessageFormDto messageFormDto, Long senderUserId);

}
