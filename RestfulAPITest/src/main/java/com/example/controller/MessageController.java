package com.example.controller;

import com.example.bean.Message;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @PostMapping(value = "/getPassword",  consumes = "application/json")
    public String getPassword(@RequestBody Message message){

        System.out.println(message);
        return "Hello, world!";
    }
}
