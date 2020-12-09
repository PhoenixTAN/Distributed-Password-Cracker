
package com.controller;

import com.bean.Job;
import com.bean.Message;
import com.utils.Cache;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {

    @Value("#{'${list}'.split(',')}")
    private List<String> ips;
    private static ConcurrentHashMap<String, String> notebook = Cache.getInstance().getNoteBook();

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/getPassword",  consumes = "application/json")
    public String getPassword(@RequestBody Message message) throws ExecutionException, InterruptedException {

        Integer numOfWorkers = message.getWorkerNum();
        final String MD5 = message.getMD5Password().toUpperCase();
        String hit = notebook.getOrDefault(MD5, "");

        if ( hit.length() > 0 ) {
            System.out.println("hit");
            System.out.println("Returning password: " + hit);
            return hit;
        }

        if ( numOfWorkers > 8 || numOfWorkers <= 0 ) {
            numOfWorkers = 8;
        }

        Dispatcher dispatcher = new Dispatcher(numOfWorkers);
        ArrayList<Job> jobList = dispatcher.getJobList();

        // send Request To Workers
        CompletableFuture<String>[] works = new CompletableFuture[numOfWorkers];

        // send async requests to workers
        for ( int i = 0; i < numOfWorkers; i++ ) {
            int finalI = i;
            works[i] = CompletableFuture.supplyAsync(() ->
                    sendRequestToWorker(jobList.get(finalI), ips.get(finalI), MD5)
            );
        }

        String password = null;

        try {

            CompletableFuture<Object> response = CompletableFuture.anyOf(works);
            password = (String) response.get();

            if ( password == null || password.length() == 0 ) {

                System.out.println("Waiting for join...");
                // print the status of works
                for( int i = 0; i < works.length; i++ ) {
                    System.out.println(works[i]);
                }

                CompletableFuture.allOf(works).join();

                for( int i = 0; i < works.length; i++ ) {
                    System.out.println(works[i]);
                }
                for ( int i = 0; i < numOfWorkers; i++ ) {
                    String result = (String) works[i].get();
                    if ( result != null && result.length() > 0) {
                        System.out.println("allOf");
                        password = result;
                        break;
                    }
                }
            }
        }
        catch(Exception exception) {
            System.out.println(exception);
            return exception.toString();
        }

        System.out.println("Returning password: " + password);

        if (notebook.size() > Cache.getInstance().getMAX_SIZE() ) {
            String removeEntry = null;
            for ( String key : notebook.keySet() ) {
                removeEntry = key;
                break;
            }
            notebook.remove(removeEntry);
        }

        notebook.put(MD5, password);

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


        System.out.println("Send request to worker." + map.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }
}
