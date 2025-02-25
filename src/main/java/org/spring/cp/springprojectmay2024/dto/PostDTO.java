package org.spring.cp.springprojectmay2024.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record PostDTO(
        Long id,
        String content,
        OffsetDateTime created,
        OffsetDateTime updated
) {
}
