package com.serhiiklymchuk.gmailpet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {

    @Id
    private Long id;

    private String senderUsername;

    private String receiverUsername;

    private String subject;

    private String content;

    private boolean reviewed;

    private LocalDateTime date;
}
