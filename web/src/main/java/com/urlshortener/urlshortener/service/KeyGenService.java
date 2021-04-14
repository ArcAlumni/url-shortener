package com.urlshortener.urlshortener.service;

import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class KeyGenService {

    @Value("${app.service.url.kgs}")
    private String kgsUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String getRandomKey() {
        try {
            return restTemplate.getForObject(kgsUrl, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting key");
        }
    }

    public String getKeyFromUrl(String url) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Internal Error");
        }
        Base62 base62 = Base62.createInstance();
        return new String(base62.encode(md.digest(url.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }

}
