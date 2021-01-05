package com.serhiiklymchuk.gmailpet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    @ResponseBody
    public String showError(HttpServletRequest req){
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");

        return String.format("<html>" +
                                 "<body>" +
                                    "<h2>Error Page</h2>" +
                                    "<div>Status code: <b>%s</b> </div>" +
                                    "<div> Error Message: <b>%s</b> </div>" +
                                 "<body>" +
                             "</html>",
                statusCode, exception==null ? "N/A": exception.getMessage());
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
