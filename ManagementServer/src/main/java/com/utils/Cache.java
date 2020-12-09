package com.utils;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private static Cache instance = new Cache();
    private static ConcurrentHashMap<String, String> notebook = new ConcurrentHashMap<>();
    private Cache() {}

    public static Cache getInstance() {
        return instance;
    }

    public ConcurrentHashMap getNoteBook() {
        return notebook;
    }

    private final int MAX_SIZE = 500000;

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}
