package com.urlshortener.urlshortener.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User implements Persistable<Integer> {

    @Id
    private Integer userId;

    private String name;

    private String email;

    @CreatedDate
    private Date createdDate;

    private Date lastLogin;

    @JsonIgnore
    @Override
    public Integer getId() {
        return userId;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return createdDate == null;
    }

}
