package com.sparta.audumbla.audumblaworldjpa.repositories;

import com.sparta.audumbla.audumblaworldjpa.entities.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
