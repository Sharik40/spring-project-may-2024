package org.spring.cp.springprojectmay2024.controller;

import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.UserDTO;
import org.spring.cp.springprojectmay2024.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> filterUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(userService.filterUsers(username, email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails user) {
        userService.deleteUserByUsername(user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody UserDTO updatedUser) {
        return ResponseEntity.ok(userService.updateUser(userDetails.getUsername(), updatedUser));
    }
}
