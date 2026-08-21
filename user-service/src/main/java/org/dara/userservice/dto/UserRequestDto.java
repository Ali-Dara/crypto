package org.dara.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        String fullName,

        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "Invalid phone number"
        )
        String phone,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {}
