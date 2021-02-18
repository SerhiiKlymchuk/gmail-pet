package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.MessageDto;
import com.serhiiklymchuk.gmailpet.dto.MessageFormDto;
import com.serhiiklymchuk.gmailpet.service.MessageService;
import com.serhiiklymchuk.gmailpet.service.SendMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String getInboxMessages(
            @AuthenticationPrincipal User user, Model model,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MessageDto> inboxMessages = messageService.getInboxMessages(user, pageable);

        model.addAttribute("messages", inboxMessages);
        model.addAttribute("sendUrl", "inbox");

        return "messages/inbox";
    }

    @GetMapping("/outbox")
    public String getOutboxMessages(
            @AuthenticationPrincipal User user, Model model,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MessageDto> outboxMessages = messageService.getOutboxMessages(user, pageable);

        model.addAttribute("messages", outboxMessages);
        model.addAttribute("sendUrl", "outbox");

        return "messages/outbox";
    }

    @GetMapping("/search-inbox")
    public String searchInboxMessages(
            @AuthenticationPrincipal User user, String searchQuery, Model model,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MessageDto> inboxMessagesSearch = messageService.searchInboxMessages(user, searchQuery, pageable);

        model.addAttribute("messages", inboxMessagesSearch);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("sendUrl", "search-inbox");

        return "messages/inbox";
    }

    @GetMapping("/search-outbox")
    public String searchOutboxMessages(
            @AuthenticationPrincipal User user, String searchQuery, Model model,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MessageDto> outboxMessagesSearch = messageService.searchOutboxMessages(user, searchQuery, pageable);

        model.addAttribute("messages", outboxMessagesSearch);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("sendUrl", "search-outbox");

        return "messages/outbox";
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

    @GetMapping("/recycle-bin")
    public String recycleBin(@AuthenticationPrincipal User user, Model model,
                             @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MessageDto> recycleBinMessages = messageService.getRecycleBinMessages(user, pageable);

        model.addAttribute("messages", recycleBinMessages);
        model.addAttribute("sendUrl", "recycle-bin");

        return "messages/recycle";
    }

    @PostMapping("/recycle-bin/{id}")
    public String moveToRecycle(@AuthenticationPrincipal User user, @PathVariable Long id, RedirectAttributes attr) {

        MessageDto message = messageService.findById(user, id);
        messageService.recycleMessage(user, message);

        attr.addFlashAttribute("messageSuccess", "Message was successfully moved to recycle bin!");

        if (user.getUsername().equals(message.getReceiverUsername())) {
            return "redirect:/messages/inbox";
        }

        return "redirect:/messages/outbox";

    }
}
