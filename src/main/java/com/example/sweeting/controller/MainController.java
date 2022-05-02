package com.example.sweeting.controller;

import com.example.sweeting.model.Message;
import com.example.sweeting.model.User;
import com.example.sweeting.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository repository;

    @GetMapping("/")
    public String greeting() {
        return "greetings";
    }


    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model) {
        Iterable<Message> messages = repository.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = repository.findByTag(filter);
        } else {
            messages = repository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag, Map<String, Object> model) {

        Message message = new Message(text, tag, user);
        repository.save(message);

        Iterable<Message> messages = repository.findAll();
        model.put("some", messages);

        return "redirect:/main";
    }

}