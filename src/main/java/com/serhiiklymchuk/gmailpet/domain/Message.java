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

    private Long ownerUserId;

    private Long receiverUserId;

    private String subject;

    private String content;

    private boolean reviewed;

    private LocalDateTime date;
}
