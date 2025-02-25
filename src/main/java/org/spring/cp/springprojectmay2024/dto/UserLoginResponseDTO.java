package org.spring.cp.springprojectmay2024.dto;

import lombok.Builder;

@Builder
public record UserLoginResponseDTO(
        String accessToken,
        String refreshToken
) {
}
