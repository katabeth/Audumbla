package com.sparta.audumbla.audumblaworldjpa.service;

import com.sparta.audumbla.audumblaworldjpa.entities.ApiKey;
import com.sparta.audumbla.audumblaworldjpa.repositories.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class APIService {
    private final ApiKeyRepository apiKeyRepository;
    @Autowired
    public APIService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey getApiKeyByApiKey(String APIKey) {
        return apiKeyRepository.findAll().stream()
                .filter(key -> key.getApiKey().equals(APIKey))
                .findFirst().orElse(null);
    }

    public Optional<String> generateAPIKey(String role) {
        if (!role.equals("FULL_ACCESS") && !role.equals("READ_ONLY"))
            return Optional.empty();

        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(UUID.randomUUID().toString());
        apiKey.setRole(role);
        apiKeyRepository.save(apiKey);

        return Optional.of(apiKey.getApiKey());
    }
}
