package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/Mjolnir/keygen")
public class APIController {
    private final APIService apiService;

    @Autowired
    public APIController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public ResponseEntity<String> getLanguageById(@RequestParam String role, @RequestHeader(name = "MJOLNIR-API-KEY") String apiKey) {
        String requestRole = apiService.getApiKeyByApiKey(apiKey).getRole();
        if(requestRole == null || !requestRole.equals("FULL_ACCESS"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String key = apiService.generateAPIKey(role).orElseThrow();

        return ResponseEntity.ok(key);
    }
}
