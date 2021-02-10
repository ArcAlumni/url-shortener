package com.urlshortener.kgs.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UsedKey {

    @Id
    private String key;

    public UsedKey() {
    }

    public UsedKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
