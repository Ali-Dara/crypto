package org.dara.userservice.dto;

import java.time.LocalDate;

public record UserResponseDto(
        String userUUID,
        String fullName,
        String phone,
        String email,
        LocalDate registerDate,
        LocalDate birthDate
) {}
