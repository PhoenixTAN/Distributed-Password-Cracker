package com.controller;

import com.utils.Worker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MessageController {
    @PostMapping(value = "/decryptPassword")
    public String decryptPassword(@RequestBody Map<String, String> map){
        String MD5 = map.get("password");
        String start = map.get("start");
        String end = map.get("end");
        String password = null;
        Worker worker = new Worker();

        System.out.println(MD5);
        System.out.println(start);
        System.out.println(end);

        password = worker.bruteForcingPassword(start, end, MD5);

        System.out.println(password);
        return password;
    }
}
