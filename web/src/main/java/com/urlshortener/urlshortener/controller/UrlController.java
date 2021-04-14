package com.urlshortener.urlshortener.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.urlshortener.urlshortener.model.UrlInfo;
import com.urlshortener.urlshortener.repository.UrlInfoRepository;

import com.urlshortener.urlshortener.service.KeyGenService;
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

    @Autowired
    private KeyGenService keyGenService;

    @PostMapping("/url")
    public String addUrl(@RequestBody UrlInfo urlInfo, HttpServletRequest request) {
        //TODO validate URL
        String hash = keyGenService.getRandomKey();
        urlInfo.setIdHash(hash);
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


}
