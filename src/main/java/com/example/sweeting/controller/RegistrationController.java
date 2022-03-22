package com.example.sweeting.controller;

import com.example.sweeting.model.Role;
import com.example.sweeting.model.User;
import com.example.sweeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String createUser(User user, Map<String, Object> model) {
        User userFromRepo = repository.findByName(user.getUsername());

        if (userFromRepo!=null ){
            model.put("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        repository.save(user);

        return "redirect:/login";

    }
}
