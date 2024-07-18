package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Cow/keygen")
public class APIController {
    private final APIService apiService;

    @Autowired
    public APIController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public ResponseEntity<String> generateNewAPIKey(@RequestParam String role, @RequestHeader(name = "COW-API-KEY") String apiKey) {
        String requestRole = apiService.getApiKeyEntityByApiKey(apiKey).getRole();
        if(requestRole == null || !requestRole.equals("FULL_ACCESS"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String key = apiService.generateAPIKey(role).orElseThrow();
        return ResponseEntity.ok(key);
    }
}
