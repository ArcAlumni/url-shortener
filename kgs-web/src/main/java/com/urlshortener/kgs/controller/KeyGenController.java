package com.urlshortener.kgs.controller;

import com.urlshortener.kgs.service.KeyGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class KeyGenController {

    @Autowired
    private KeyGenService keyGenService;

    @GetMapping
    public String getRandomKey() {
        return keyGenService.getRandomKey();
    }

}
