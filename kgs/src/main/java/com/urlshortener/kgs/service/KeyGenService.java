package com.urlshortener.kgs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyGenService {

    @Autowired
    private ServiceCache serviceCache;

    public String getRandomKey() {
        return serviceCache.nextRandom();
    }

}
