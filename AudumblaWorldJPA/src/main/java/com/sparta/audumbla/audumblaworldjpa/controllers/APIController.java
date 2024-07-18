package com.sparta.audumbla.audumblaworldjpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/Mjolnir/keygen")
public class APIController {
    private final ApiService apiService;

    @Autowired
    public MjolnirApiController(MjolnirApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public ResponseEntity<String> getLanguageById(@RequestParam String role, @RequestHeader(name = "MJOLNIR-API-KEY") String apiKey) {
        String requestRole = apiService.getRoleFromApiKey(apiKey);
        if(requestRole == null || !requestRole.equals("FULL_ACCESS"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user.");

        String key = apiService.generateApiKey(role).orElseThrow(() -> new InvalidBodyException("Invalid role provided"));

        return ResponseEntity.ok(key);
    }
}
