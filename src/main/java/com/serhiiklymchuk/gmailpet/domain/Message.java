package com.serhiiklymchuk.gmailpet.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private Long id;

    private Long senderUserId;

    private Long receiverUserId;

    private String subject;

    private String content;

    private MessageStatus receiveStatus;

    private MessageStatus sendStatus;

    private boolean reviewed;

    private LocalDateTime date;
}
