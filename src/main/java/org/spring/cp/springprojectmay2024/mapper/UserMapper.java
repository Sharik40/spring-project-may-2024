package org.spring.cp.springprojectmay2024.mapper;

import org.mapstruct.Mapper;
import org.spring.cp.springprojectmay2024.dto.UserDTO;
import org.spring.cp.springprojectmay2024.dto.UserResponceDTO;
import org.spring.cp.springprojectmay2024.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    UserResponceDTO userToUserResponceDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
