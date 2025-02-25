package org.spring.cp.springprojectmay2024.service;

import lombok.RequiredArgsConstructor;
import org.spring.cp.springprojectmay2024.dto.CreatePostDTO;
import org.spring.cp.springprojectmay2024.dto.PostDTO;
import org.spring.cp.springprojectmay2024.entity.Post;
import org.spring.cp.springprojectmay2024.entity.User;
import org.spring.cp.springprojectmay2024.mapper.PostMapper;
import org.spring.cp.springprojectmay2024.repository.PostRepository;
import org.spring.cp.springprojectmay2024.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostMapper postMapper;

    @Transactional
    public PostDTO create(String username, CreatePostDTO createPostDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postMapper.postDTOToPost(createPostDTO);
        post.setUser(user);
        return postMapper.postToPostDTO(postRepository.save(post));
    }

    public List<PostDTO> getAllPosts(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getPosts().stream().map(postMapper::postToPostDTO).collect(Collectors.toList());
    }

    public PostDTO updatePost(String username, CreatePostDTO createPostDTO, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Please select a post"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Please select a post");
        }

        post.setContent(createPostDTO.content());

        return postMapper.postToPostDTO(postRepository.save(post));
    }

    @Transactional
    public void deletePost(String username, Long postId) {
        if (!postRepository.existsByIdAndUserUsername(postId, username)) {
            throw new IllegalArgumentException("Please select a post");
        }
        postRepository.deleteById(postId);
    }
}
