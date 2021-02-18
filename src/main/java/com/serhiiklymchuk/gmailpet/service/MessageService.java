package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    void createMessage(MessageFormDto messageFormDto, Long senderUserId);

    void recycleMessage(User user, MessageDto message);

    MessageDto findById(User user, Long messageId);

    Page<MessageDto> getInboxMessages(User user, Pageable pageable);

    Page<MessageDto> getOutboxMessages(User user, Pageable pageable);

    Page<MessageDto> getRecycleBinMessages(User user, Pageable pageable);

    Page<MessageDto> searchInboxMessages(User user, String searchQuery, Pageable pageable);

    Page<MessageDto> searchOutboxMessages(User user, String searchQuery, Pageable pageable);

}
