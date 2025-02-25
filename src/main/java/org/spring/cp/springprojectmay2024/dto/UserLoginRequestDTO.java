package org.spring.cp.springprojectmay2024.dto;

import lombok.Builder;

@Builder
public record UserLoginRequestDTO(
        String username,
        String password
) {

}
