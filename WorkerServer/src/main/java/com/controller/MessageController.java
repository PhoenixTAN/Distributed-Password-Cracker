package com.controller;

import com.utils.Cache;
import com.utils.Worker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class MessageController {

    private static ConcurrentHashMap<String, String> notebook = Cache.getInstance().getNoteBook();

    @PostMapping(value = "/decryptPassword")
    public String decryptPassword(@RequestBody Map<String, String> map){
        String MD5 = map.get("password");
        String hit = notebook.getOrDefault(MD5.toUpperCase(), "");
        if ( hit.length() > 0 ) {
            System.out.println("hit");
            return hit;
        }

        String start = map.get("start");
        String end = map.get("end");
        String password = null;
        Worker worker = new Worker();

        System.out.println(MD5);
        System.out.println(start);
        System.out.println(end);

        password = worker.bruteForcingPassword(start, end, MD5);

        if (notebook.size() > Cache.getInstance().getMAX_SIZE() ) {
            String removeEntry = null;
            for ( String key : notebook.keySet() ) {
                removeEntry = key;
                break;
            }
            notebook.remove(removeEntry);
        }

        notebook.put(MD5, password);

        // System.out.println(password);
        return password;
    }
}
