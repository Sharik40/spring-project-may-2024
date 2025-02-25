package org.spring.cp.springprojectmay2024.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.CreatePostDTO;
import org.spring.cp.springprojectmay2024.dto.PostDTO;
import org.spring.cp.springprojectmay2024.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid CreatePostDTO createPostDTO,
                                     @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(postService.create(user.getUsername(), createPostDTO));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable String username) {
        return ResponseEntity.ok(postService.getAllPosts(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        postService.deletePost(user.getUsername(), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody @Valid CreatePostDTO createPostDTO,
                                              @AuthenticationPrincipal UserDetails user, @PathVariable Long id) {
        return ResponseEntity.ok(postService.updatePost(user.getUsername(), createPostDTO, id));
    }

}
