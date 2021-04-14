package com.urlshortener.kgsworker.repository;

import com.urlshortener.kgsworker.model.UsedKey;
import org.springframework.data.repository.CrudRepository;

public interface UsedKeysRepository extends CrudRepository<UsedKey, String> {

}
