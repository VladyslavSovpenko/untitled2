package com.example.sweeting.controller;

import com.example.sweeting.repository.MessageRepository;
import com.example.sweeting.model.Message;
import com.example.sweeting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository repository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = repository.findAll();
        model.put("some", messages);
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

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages = null;
        if (filter != null && !filter.isEmpty()) {
            messages = repository.findByTag(filter);
        } else {
            repository.findAll();
        }
        model.put("some", messages);
        return "main";
    }

}