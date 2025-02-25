package org.spring.cp.springprojectmay2024.repository;

import org.spring.cp.springprojectmay2024.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByIdAndUserUsername(Long id, String username);
}
