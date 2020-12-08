
package com.controller;

import com.bean.Job;
import com.bean.Message;
import com.utils.Dispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {

    @Value("#{'${list}'.split(',')}")
    private List<String> ips;

//    @Value("${verbose}")
//    private String verbose;

    @PostMapping(value = "/getPassword",  consumes = "application/json")
    public String getPassword(@RequestBody Message message) throws ExecutionException, InterruptedException {

        Integer numOfWorkers = message.getWorkerNum();
        Dispatcher dispatcher = new Dispatcher(numOfWorkers);
        ArrayList<Job> jobList = dispatcher.getJobList();

        // send Request To Workers
        CompletableFuture<String>[] works = new CompletableFuture[numOfWorkers];

        // send async requests to workers
        for ( int i = 0; i < numOfWorkers; i++ ) {
            int finalI = i;
            works[i] = CompletableFuture.supplyAsync(() ->
                    sendRequestToWorker(jobList.get(finalI), ips.get(finalI), message.getMD5Password())
            );
        }

        // if one request is completed, we get the result
        // we don't have to wait other async request finished.
        CompletableFuture<Object> response = CompletableFuture.anyOf(works);
        String password = (String) response.get();

        // TODO: if it returns a "", we have to wait another request.
        if ( password.length() == 0 ) {

            //if ( verbose.equals("1") ) {
                System.out.println("Waiting for join...");
            //}

            CompletableFuture.allOf(works).join();
            for ( int i = 0; i < numOfWorkers; i++ ) {
                String result = (String)works[i].get();
                if (result.length() > 0) {
                    password = result;
                    break;
                }
            }
        }

        //if ( verbose.equals("1") ) {
            System.out.println("Returning password: " + password);
        //}

        return password;
    }

    private String sendRequestToWorker(Job job, String ip, String MD5Password)  {

        String url = "http://" + ip + "/decryptPassword";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map = new HashMap<>();
        map.put("password", MD5Password);
        map.put("start", job.getStart());
        map.put("end", job.getEnd());

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map, headers);

        //if ( verbose.equals("1") ) {
            System.out.println("Send request to worker." + map.toString());
        //}

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }
}
