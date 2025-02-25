package org.spring.cp.springprojectmay2024.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorDTO(String message, OffsetDateTime time) {
}
