package org.dara.userservice.dto;

import java.time.LocalDate;

public record UserRequestDto(
        String fullName,
        String phone,
        LocalDate birthDate
) {}
