package org.dara.userservice.dto;

import java.time.LocalDate;

public record UserResponseDto(
        String id,
        String name,
        String email,
        LocalDate registerDate,
        LocalDate birthDate
) {}
