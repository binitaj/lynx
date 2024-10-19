package com.binitajha.lynx.server.repository;

import com.binitajha.lynx.server.model.Secret;
import org.springframework.data.repository.CrudRepository;

public interface SecretsRepository extends CrudRepository<Secret, String> {
}
