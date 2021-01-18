package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.dto.UserDto;
import com.serhiiklymchuk.gmailpet.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome() {
        return "user/home";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String createUser(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes attr, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/register";
        }

        userService.createUser(userDto);
        attr.addFlashAttribute("user", userDto);

        return "redirect: home";
    }

}
