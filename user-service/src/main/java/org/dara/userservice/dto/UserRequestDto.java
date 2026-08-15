package org.dara.userservice.dto;

import java.time.LocalDate;

public record UserRequestDto(
        String name,
        String phone,
        LocalDate birthDate
) {}
