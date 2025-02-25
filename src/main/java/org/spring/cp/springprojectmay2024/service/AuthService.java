package org.spring.cp.springprojectmay2024.service;

import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.UserDTO;
import org.spring.cp.springprojectmay2024.dto.UserLoginRequestDTO;
import org.spring.cp.springprojectmay2024.dto.UserLoginResponseDTO;
import org.spring.cp.springprojectmay2024.entity.User;
import org.spring.cp.springprojectmay2024.error.UsernameAlreadyExistsException;
import org.spring.cp.springprojectmay2024.mapper.UserMapper;
import org.spring.cp.springprojectmay2024.repository.UserRepository;
import org.spring.cp.springprojectmay2024.util.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userMapper.userToUserDTO(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(e.getMessage());
        }
    }

    @Transactional
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequestDTO.username(), userLoginRequestDTO.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            UserDetails user = userService.loadUserByUsername(userLoginRequestDTO.username());
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);
            return new UserLoginResponseDTO(accessToken, refreshToken);
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
