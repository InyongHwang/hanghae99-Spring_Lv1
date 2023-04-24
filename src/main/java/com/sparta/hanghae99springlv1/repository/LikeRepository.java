package com.sparta.hanghae99springlv1.repository;

import com.sparta.hanghae99springlv1.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByPostIdAndUser_Id(Long postId, Long userId);
    Like findByReplyIdAndUser_Id(Long replyId, Long userId);
    void deleteAllByReplyId(Long replyId);
    void deleteAllByPostId(Long postId);
}
