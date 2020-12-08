
package com.controller;

import com.bean.Job;
import com.bean.Message;
import com.utils.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {

    @Value("#{'${list}'.split(',')}")
    public List<String> ips;

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/getPassword",  consumes = "application/json")
    public String getPassword(@RequestBody Message message) throws ExecutionException, InterruptedException {

        Integer numOfWorkers = message.getWorkerNum();
        Dispatcher dispatcher = new Dispatcher(numOfWorkers);
        ArrayList<Job> jobList = dispatcher.getJobList();
        String password = null;
        Collections.reverse(jobList);

        // send Request To Workers
        CompletableFuture<String>[] works = new CompletableFuture[numOfWorkers];

        for ( int i = 0; i < numOfWorkers; i++ ) {
            int finalI = i;
            works[i] = CompletableFuture.supplyAsync(() ->
                    sendRequestToWorker(jobList.get(finalI), ips.get(finalI), message.getMD5Password())
            );
        }

        CompletableFuture<Object> result = CompletableFuture.anyOf(works);

        return (String) result.get();
    }

    public String sendRequestToWorker(Job job, String ip, String MD5Password)  {


        String url = "http://" + ip + "/decryptPassword";

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> map = new HashMap<>();
        map.put("password", MD5Password);
        map.put("start", job.getStart());
        map.put("end", job.getEnd());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map, headers);
        System.out.println("Send request to worker, password: " + map.toString());
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }
}
