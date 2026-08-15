package org.dara.userservice.dto;

import java.time.LocalDate;

public record UserResponseDto(
        String userUUID,
        String name,
        String phone,
        LocalDate registerDate,
        LocalDate birthDate
) {}
