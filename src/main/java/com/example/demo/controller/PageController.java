package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping("/dashboard")
    public String Dashboard() {
        return "dash";
    }

    @GetMapping("/admin")
    public String Admin() {
        return "admin";
    }

    @GetMapping("/main")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
