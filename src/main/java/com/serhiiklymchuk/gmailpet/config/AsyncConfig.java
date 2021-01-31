package com.serhiiklymchuk.gmailpet.config;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.SendMessageException;
import com.serhiiklymchuk.gmailpet.repository.MessageRepository;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.time.LocalDateTime;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private static final Long GMAIL_SUPPORT_ID = 1L;

    private final MessageRepository messageRepository;

    public AsyncConfig(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, obj) -> {

            if (throwable.getClass().equals(SendMessageException.class)) {

                MessageFormDto messageFormDto = (MessageFormDto) obj[0];
                Long senderUserId = (Long) obj[1];

                Message message = Message.builder()
                        .senderUserId(GMAIL_SUPPORT_ID)
                        .receiverUserId(senderUserId)
                        .subject("Your message wasn't delivered!")
                        .content("Hey, user with username `"
                                + messageFormDto.getReceiverUsername()
                                + "` was not found! And message wasn't delivered!")
                        .reviewed(false)
                        .date(LocalDateTime.now())
                        .build();

                messageRepository.save(message);
            }
        };
    }

}
