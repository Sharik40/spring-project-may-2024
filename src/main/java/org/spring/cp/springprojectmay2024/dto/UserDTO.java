package org.spring.cp.springprojectmay2024.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDTO(

        @NotBlank(message = "Username must not be empty")
        @Size(min = 2, max = 15, message = "Username must be from 2 to 15 symbols")
        String username,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 2, max = 20, message = "Password must be from 2 to 20 symbols")
        String password,

        @NotBlank(message = "Email must not be empty")
        @Email
        String email
) {
}
