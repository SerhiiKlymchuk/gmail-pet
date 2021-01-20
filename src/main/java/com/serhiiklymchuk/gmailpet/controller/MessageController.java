package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.service.impl.MessageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/inbox")
    public String getInboxMessages(Principal principal, Model model) {

        List<Message> inboxMessages = messageService.getInboxMessages(principal.getName());
        model.addAttribute("inboxMessages", inboxMessages);

        return "messages/inbox";
    }

    @GetMapping("/outbox")
    public String getOutboxMessages(Principal principal, Model model) {

        List<Message> outboxMessages = messageService.getOutboxMessages(principal.getName());
        model.addAttribute("outboxMessages", outboxMessages);

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
