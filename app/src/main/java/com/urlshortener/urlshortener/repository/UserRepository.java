package com.urlshortener.urlshortener.repository;

import com.urlshortener.urlshortener.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

}
