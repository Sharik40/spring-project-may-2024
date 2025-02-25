package org.spring.cp.springprojectmay2024.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreatePostDTO(
        @NotBlank
        @Size(min = 2, max = 255, message = "post must be from 2 to 255 symbols")
        String content
) {
}

