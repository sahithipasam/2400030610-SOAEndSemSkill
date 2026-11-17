package com.tourist.controller;

import com.tourist.dto.*;
import com.tourist.service.TouristService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {
    private final TouristService service;
    private final String expectedToken;

    public TouristController(TouristService service, @Value("${app.token.value}") String expectedToken) {
        this.service = service;
        this.expectedToken = expectedToken;
    }

    @GetMapping
    public Object all() { return service.findAll(); }

    @GetMapping("/{id}")
    public TouristResponse one(@PathVariable Long id) { return service.find(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TouristResponse create(@Valid @RequestBody TouristRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestHeader(value = "token_value", required = false) String token,
            @Valid @RequestBody TouristRequest request) {
        if (!expectedToken.equals(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return ResponseEntity.ok(Map.of(
            "message", "Tourist updated successfully",
            "tourist", service.update(id, request)));
    }
}
