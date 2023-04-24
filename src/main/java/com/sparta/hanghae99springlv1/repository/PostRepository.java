package com.sparta.hanghae99springlv1.repository;

import com.sparta.hanghae99springlv1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
    Optional<Post> findByIdAndUser_Id(Long postId, Long userId);
}
