package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.service.impl.MessageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/inbox")
    public String getInboxMessages() {
        return "messages/inbox";
    }

    @GetMapping("/outbox")
    public String getOutboxMessages() {
        return "messages/outbox";
    }

    @GetMapping("/new")
    public String getNewMessage() {
        return "messages/new";
    }

    @PostMapping("/new")
    public String createMessage() {
        return "redirect:/messages/outbox";
    }
}
