package com.sparta.hanghae99springlv1.repository;

import com.sparta.hanghae99springlv1.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPostIdOrderByCreateAtDesc(Long id);
    void deleteAllByPostId(Long id);
}
