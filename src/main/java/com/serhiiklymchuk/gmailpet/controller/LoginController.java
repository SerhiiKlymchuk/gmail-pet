package com.serhiiklymchuk.gmailpet.controller;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public String getLoginPage(){
        return "login/login";
    }

    @PostMapping
    public String tryLogin(@RequestParam("username") String username, RedirectAttributes attr){
        User user = userService.findByUsername(username);
        attr.addFlashAttribute("user", user);

        return "redirect:user/home";
    }

}
