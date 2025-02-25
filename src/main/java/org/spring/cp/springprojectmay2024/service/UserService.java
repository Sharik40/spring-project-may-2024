package org.spring.cp.springprojectmay2024.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.UserDTO;
import org.spring.cp.springprojectmay2024.entity.User;
import org.spring.cp.springprojectmay2024.error.UsernameAlreadyExistsException;
import org.spring.cp.springprojectmay2024.mapper.UserMapper;
import org.spring.cp.springprojectmay2024.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO findByUsername(String username) {
        return userMapper.userToUserDTO(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    public List<UserDTO> filterUsers(String username, String email) {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .filter(user -> (email == null || user.getEmail().equalsIgnoreCase(email)))
                .filter(user -> (username == null || user.getUsername().equalsIgnoreCase(username)))
                .map(userMapper::userToUserDTO)
                .toList();
        if (!users.isEmpty()) {
            return users;
        } else {
            System.out.println("User not found");
            throw new NoSuchElementException();
        }
    }


    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserDTO updateUser(String username, UserDTO updatedUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
            if (updatedUser.email() != null) user.setEmail(updatedUser.email());
            if (updatedUser.username() != null) user.setUsername(updatedUser.username());
            if (updatedUser.password() != null) user.setPassword(updatedUser.password());
            return userMapper.userToUserDTO(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(e.getMessage());
        }

    }
}
