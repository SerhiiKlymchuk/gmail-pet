package com.serhiiklymchuk.gmailpet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageFormDto {

    @NotBlank
    private String receiverUsername;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    private LocalDateTime date;
}
