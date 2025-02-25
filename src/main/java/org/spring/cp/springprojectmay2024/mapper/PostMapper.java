package org.spring.cp.springprojectmay2024.mapper;

import org.mapstruct.Mapper;
import org.spring.cp.springprojectmay2024.dto.CreatePostDTO;
import org.spring.cp.springprojectmay2024.dto.PostDTO;
import org.spring.cp.springprojectmay2024.entity.Post;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface PostMapper {
    default PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id(post.getId());
        postDTO.content(post.getContent());
        postDTO.created(post.getCreatedAt());
        postDTO.updated(post.getUpdatedAt());

        return postDTO.build();
    }

    default Post postDTOToPost(CreatePostDTO createPostDTO) {
        if ( createPostDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setContent( createPostDTO.content());
        post.setCreatedAt(OffsetDateTime.now());
        post.setUpdatedAt(OffsetDateTime.now());

        return post;
    }
}
