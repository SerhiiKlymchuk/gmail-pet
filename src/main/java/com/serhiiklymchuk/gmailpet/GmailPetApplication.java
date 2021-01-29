package com.serhiiklymchuk.gmailpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GmailPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmailPetApplication.class, args);
    }

}
