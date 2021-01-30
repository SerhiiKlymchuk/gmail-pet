package com.serhiiklymchuk.gmailpet.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(MessageException.class)
    public String messageException(MessageException e, RedirectAttributes attr) {

        attr.addFlashAttribute("messageFailure", e.getMessage());

        return "redirect:/messages/new";
    }

    @ExceptionHandler(BindException.class)
    public String validationException(RedirectAttributes attr) {

        attr.addFlashAttribute("error", "Fill Empty Fields!");

        return "redirect:/messages/new";
    }

}
