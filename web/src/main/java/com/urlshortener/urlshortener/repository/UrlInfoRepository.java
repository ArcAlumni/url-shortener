package com.urlshortener.urlshortener.repository;

import com.urlshortener.urlshortener.model.UrlInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlInfoRepository extends MongoRepository<UrlInfo, String> {

    Optional<UrlInfo> findByIdHash(String idHash);

}
