package com.urlshortener.kgs.repository;

import com.urlshortener.kgs.model.RandomKey;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeysRepository extends CrudRepository<RandomKey, String> {

    List<RandomKey> findAll(Pageable pageable);

    default List<RandomKey> findAll() {
        return findAll(PageRequest.of(0, 20));
    }

}
