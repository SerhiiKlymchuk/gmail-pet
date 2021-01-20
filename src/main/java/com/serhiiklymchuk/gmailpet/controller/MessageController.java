package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.service.impl.MessageServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
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
    public String getNewMessage(Model model, String error) {

        if (nonNull(error)) {
            model.addAttribute("error", error);
        }

        return "messages/new";
    }

    @PostMapping("/new")
    public String createMessage(@AuthenticationPrincipal User user, @Valid MessageFormDto messageFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/messages/new?error=Fill Empty Fields!";
        }

        if (!messageService.createMessage(messageFormDto, user.getId())) {
            return "redirect:/messages/new?error=Receiver Not Found!";
        }

        return "redirect:/messages/outbox";
    }

}
