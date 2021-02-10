package com.urlshortener.kgs.repository;

import com.urlshortener.kgs.model.UsedKey;
import org.springframework.data.repository.CrudRepository;

public interface UsedKeysRepository extends CrudRepository<UsedKey, String> {

}
