package com.serhiiklymchuk.gmailpet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@Controller
public class LoginController{

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout){

        if (nonNull(error)) {
            model.addAttribute("errorMessage", "Incorrect username or password!!");
        }
        if (nonNull(logout))
            model.addAttribute("logoutMessage", "You've successfully logged out!");

        return "login/login";

    }
}
