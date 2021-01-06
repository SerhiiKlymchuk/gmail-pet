package com.serhiiklymchuk.gmailpet.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;

@Controller
public class ApplicationErrorController implements ErrorController {

    @GetMapping("/error")
    public String showError(HttpServletRequest req, Model model){
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");

        model.addAttribute("statusCode",statusCode);
        model.addAttribute("errorMessage", exception==null ? "N/A": exception.getMessage());

        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
