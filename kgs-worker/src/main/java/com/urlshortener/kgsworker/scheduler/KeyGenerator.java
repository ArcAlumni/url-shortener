package com.urlshortener.kgsworker.scheduler;

import com.urlshortener.kgsworker.jedis.JedisClient;
import com.urlshortener.kgsworker.model.RandomKey;
import com.urlshortener.kgsworker.model.UsedKey;
import com.urlshortener.kgsworker.repository.KeysRepository;
import com.urlshortener.kgsworker.repository.UsedKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class KeyGenerator {

    private static final char[] BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final String REDIS_KEY = "ukey";
    private static final int DEFAULT_KEY_LENGTH = 8;
    private static final int BATCH_SIZE = 10;
    private static final int MAX_ROWS = 1000;
    private static final int CACHE_CAPACITY = 500;// 500-520 approx

    @Autowired
    private UsedKeysRepository usedKeysRepository;

    @Autowired
    private KeysRepository keysRepository;

    @Autowired
    private JedisClient jedisClient;

    @Scheduled(fixedDelay = 200)
    void createKeys() {
        if (keysRepository.count() < MAX_ROWS) {
            HashSet<RandomKey> keys = new HashSet<>(10);
            for (int i = 0; i < BATCH_SIZE; i++) {
                RandomKey key = new RandomKey();
                key.setKey(generateBase62Random());
                keys.add(key);
            }
            for (RandomKey key : new HashSet<>(keys)) {
                if (keysRepository.existsById(key.getKey()) || usedKeysRepository.existsById(key.getKey())) {
                    keys.remove(key);
                }
            }
            keysRepository.saveAll(keys);
        }
    }

    @Scheduled(fixedDelay = 500)
    public void loadKeysToCache() {
        try (Jedis jedis = jedisClient.get()) {
            if (jedis.llen(REDIS_KEY) < CACHE_CAPACITY) {
                List<RandomKey> keys = keysRepository.findAll();
                try {
                    for (RandomKey key : keys) {
                        jedis.lpush(REDIS_KEY, key.getKey());
                    }
                } finally {
                    // FIXME: failure possible in batch?
                    moveKeys(keys);
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void moveKeys(List<RandomKey> keys) {
        keysRepository.deleteAll(keys);
        usedKeysRepository.saveAll(keys.stream().map(x -> new UsedKey(x.getKey())).collect(Collectors.toList()));
    }

    private String generateBase62Random() {
        // FIXME: concurrency issues?
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_KEY_LENGTH; i++) {
            Random random = new Random();
            int index = random.nextInt(62);
            sb.append(BASE62_CHARS[index]);
        }
        return sb.toString();
    }

}
