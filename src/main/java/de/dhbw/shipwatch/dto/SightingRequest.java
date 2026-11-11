package de.dhbw.shipwatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record SightingRequest(

    @NotBlank(message = "observerName must not be blank")
    String observerName,

    @NotNull(message = "observedAt must not be null")
    @Past(message = "observedAt must be in the past")
    LocalDateTime observedAt,

    String location,

    // optional, aber höchstens 500 Zeichen
    @Size(max = 500, message = "notes must be at most 500 characters")
    String notes
) {}
