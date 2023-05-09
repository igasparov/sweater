package com.example.sweater.controller;

import com.example.sweater.constant.UrlPath;
import com.example.sweater.model.Message;
import com.example.sweater.model.SecurityUser;
import com.example.sweater.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping(UrlPath.MESSAGES)
    public String messages(Model model) {
        initMessages(model);
        return "messages";
    }

    @PostMapping(UrlPath.MESSAGES)
    public String addMessage(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        messageRepository.save(new Message(text, tag, user.getUser()));

        initMessages(model);
        return "messages";
    }

    @PostMapping(UrlPath.FILTER)
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        return "messages";
    }

    private void initMessages(Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
    }
}
