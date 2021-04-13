package com.urlshortener.kgs.service;

import com.urlshortener.kgs.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class KeyGenService {

    @Autowired
    private JedisClient jedisClient;

    public String getRandomKey() {
        // FIXME: failure possible here?
        try (Jedis jedis = jedisClient.get()) {
            return jedis.rpop("ukey");
        }
    }

}
