package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/inbox")
    public String getInboxMessages(@AuthenticationPrincipal User user, Model model) {

        List<MessageDto> inboxMessages = messageService.getInboxMessages(user);

        model.addAttribute("inboxMessages", inboxMessages);

        return "messages/inbox";
    }

    @GetMapping("/outbox")
    public String getOutboxMessages(@AuthenticationPrincipal User user, Model model) {

        List<MessageDto> outboxMessages = messageService.getOutboxMessages(user);

        model.addAttribute("outboxMessages", outboxMessages);

        return "messages/outbox";
    }

    @GetMapping("/new")
    public String getNewMessage() {
        return "messages/new";
    }


    @PostMapping("/new")
    public String createMessage(@AuthenticationPrincipal User user, @Valid MessageFormDto messageFormDto, RedirectAttributes attr) {

        messageService.createMessage(messageFormDto, user.getId());

        attr.addFlashAttribute("messageSuccess", "Message Was Sent Successfully!!!");

        return "redirect:/messages/outbox";
    }

}
