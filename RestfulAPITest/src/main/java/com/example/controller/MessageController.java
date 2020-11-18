package com.example.controller;

import com.example.bean.Message;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {

    @PostMapping(value = "/getPassword",  consumes = "application/json")
    public String getPassword(@RequestBody Message message){
        System.out.println(message);

        return sendRequestToWorker(message);
    }

    public String sendRequestToWorker(Message message){

        String url = "http://localhost:8081/decryptPassword";

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> map = new HashMap<>();
        map.put("password", message.getMD5Password());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map, headers);
        System.out.println("Send request to worker, password: " + map.toString());
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }
}
