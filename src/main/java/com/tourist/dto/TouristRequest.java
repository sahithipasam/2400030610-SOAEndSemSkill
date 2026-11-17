package com.tourist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TouristRequest(
    @NotBlank String name,
    @NotBlank String destination,
    @NotBlank @Email String email,
    String phone
) {}
