package com.serhiiklymchuk.gmailpet.config;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.exception.SendMessageException;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.service.UserService;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@Configuration
public class AsyncConfig implements AsyncConfigurer, CommandLineRunner {

    private static final Long GMAIL_SUPPORT_ID = 1L;

    private final UserService userService;

    private final ApplicationContext context;

    private MessageService messageService;

    public AsyncConfig(UserService userService, ApplicationContext context) {

        this.context = context;
        this.userService = userService;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, obj) -> {

            if (throwable.getClass().equals(SendMessageException.class)) {
                User senderUser = userService.findById((Long) obj[1]);
                MessageFormDto messageFormDto = (MessageFormDto) obj[0];

                MessageFormDto responseMessageFormDto = MessageFormDto.builder()
                        .receiverUsername(senderUser.getUsername())
                        .subject("Your message wasn't delivered!")
                        .content("Hey, user with username `"
                                + messageFormDto.getReceiverUsername()
                                + "` was not found! And message wasn't delivered!")
                        .build();

                messageService.createMessage(responseMessageFormDto, GMAIL_SUPPORT_ID);
            }
        };
    }

    @Override
    public void run(String... args) {
        this.messageService = context.getBean(MessageService.class);
    }
}
