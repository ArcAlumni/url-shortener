package com.urlshortener.urlshortener.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.urlshortener.urlshortener.model.UrlInfo;
import com.urlshortener.urlshortener.repository.UrlInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.seruco.encoding.base62.Base62;

@RestController
public class UrlController {

    @Autowired
    private UrlInfoRepository urlInfoRepository;

    @PostMapping("/url")
    public String addUrl(@RequestBody UrlInfo urlInfo, HttpServletRequest request) {
        urlInfo.setIdHash(getIdHash(urlInfo.getUrl()));
        urlInfoRepository.save(urlInfo);
        return new StringBuilder().append(request.getHeader("host")).append("/").append(urlInfo.getIdHash()).toString();
    }

    @GetMapping("/{idHash}")
    public void redirect(@PathVariable String idHash, HttpServletResponse response) {
        urlInfoRepository.findByIdHash(idHash).ifPresent(u -> {
            try {
                response.sendRedirect(u.getUrl());
            } catch (IOException e) {
                throw new RuntimeException("Internal Error");
            }
        });
    }

    private String getIdHash(String url) {
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
