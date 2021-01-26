package com.serhiiklymchuk.gmailpet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {

    private Long id;

    private String senderUsername;

    private String receiverUsername;

    private String subject;

    private String content;

    private boolean reviewed;

    private LocalDateTime date;
}
