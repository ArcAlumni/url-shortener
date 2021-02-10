package com.urlshortener.kgs.service;

import com.urlshortener.kgs.scheduler.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ServiceCache {

    private static final Queue<String> q = new ConcurrentLinkedQueue<>();

    @Autowired
    private KeyGenerator keyGenerator;

    public int getSize() {
        return q.size();
    }

    public void addToCache(String newKey) {
        q.offer(newKey);
    }

    public String nextRandom() {
        if (q.isEmpty())
            keyGenerator.loadKeysToCache();
        return q.poll();
    }

}
