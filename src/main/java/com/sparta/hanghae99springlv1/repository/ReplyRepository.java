package com.sparta.hanghae99springlv1.repository;

import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPost_IdOrderByCreateAtDesc(Long postId);
    void deleteAllByPost_Id(Long postId);
    Optional<Reply> findByIdAndUser_Id(Long replyId, Long userId);
}
