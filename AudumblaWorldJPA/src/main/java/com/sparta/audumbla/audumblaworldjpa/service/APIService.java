package com.sparta.audumbla.audumblaworldjpa.service;

import com.sparta.audumbla.audumblaworldjpa.entities.ApiKey;
import com.sparta.audumbla.audumblaworldjpa.repositories.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class APIService {
    private final ApiKeyRepository apiKeyRepository;
    @Autowired
    public APIService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }
    public ApiKey getApiKeyByApiKey(String APIKey) {

    }
    public String getRoleFromAPIKey(String apiKey) {
        ApiKey key = apiKeyRepository.find
    }
    public Optional<String> generateAPIKey(String role) {

    }
}
