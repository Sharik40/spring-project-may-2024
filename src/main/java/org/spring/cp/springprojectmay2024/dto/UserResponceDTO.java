package org.spring.cp.springprojectmay2024.dto;

import lombok.Builder;

@Builder
public record UserResponceDTO(
        String username,
        String email
) {
}
