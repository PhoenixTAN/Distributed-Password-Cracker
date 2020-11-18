package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController {
    @PostMapping(value = "/decryptPassword")
    public String decryptPassword(@RequestBody Map map){
        System.out.println(map.get("password"));

        return "Hello, world";
    }
}
