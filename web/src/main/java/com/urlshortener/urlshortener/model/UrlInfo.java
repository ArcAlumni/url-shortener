package com.urlshortener.urlshortener.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document
public class UrlInfo implements Persistable<String> {

    @JsonIgnore
    @Id
    private String idHash;

    private String url;

    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    private Date expirationDate;

    @JsonIgnore
    @Override
    public String getId() {
        return idHash;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return createdDate == null;
    }

}
