package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.service.SendMessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    private final SendMessageService sendMessageService;

    public MessageController(MessageService messageService, SendMessageService sendMessageService) {
        this.messageService = messageService;
        this.sendMessageService = sendMessageService;
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

    @GetMapping("/search-inbox")
    public String searchInboxMessages(@AuthenticationPrincipal User user, Model model) {

        String searchQuery = (String) model.getAttribute("searchQuery");

        List<MessageDto> inboxMessagesSearch = messageService.searchInboxMessages(user, searchQuery);

        model.addAttribute("inboxMessages", inboxMessagesSearch);

        return "messages/inbox";
    }

    @GetMapping("/search-outbox")
    public String searchOutboxMessages(@AuthenticationPrincipal User user, Model model) {

        String searchQuery = (String) model.getAttribute("searchQuery");

        List<MessageDto> outboxMessagesSearch = messageService.searchOutboxMessages(user, searchQuery);

        model.addAttribute("outboxMessages", outboxMessagesSearch);

        return "messages/outbox";
    }


    @PostMapping("/search")
    public String searchMessages(@RequestHeader String referer, @RequestParam("search_query") String searchQuery, RedirectAttributes attr) {

        attr.addFlashAttribute("searchQuery", searchQuery);

        if (referer.endsWith("/inbox") || referer.endsWith("/search-inbox")) {
            return "redirect:/messages/search-inbox";
        } else if (referer.endsWith("/outbox") || referer.endsWith("/search-outbox")) {
            return "redirect:/messages/search-outbox";
        }

        return "redirect:/messages/inbox";
    }

    @GetMapping("/new")
    public String getNewMessage() {
        return "messages/new";
    }

    @PostMapping("/new")
    public String createMessage(@AuthenticationPrincipal User user, @Valid MessageFormDto messageFormDto, RedirectAttributes attr) {

        sendMessageService.sendMessage(messageFormDto, user.getId());

        attr.addFlashAttribute("messageSuccess", "Message Was Sent Successfully!!!");

        return "redirect:/messages/outbox";
    }

}
