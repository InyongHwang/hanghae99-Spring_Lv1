package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.entity.*;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.LikeRepository;
import com.sparta.hanghae99springlv1.repository.PostRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

    // Post 좋아요
    @Transactional
    public Message postLike(Long postId, User user) {

        // 1. 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like heart = likeRepository.findByPostIdAndUser_Id(postId, user.getId());

        // 좋아요 없으면 DB에 추가
        if (heart == null) {
            likeRepository.save(new Like(user, postId, null));
            post.like();
            return CustomStatus.PostLikeSuccess.toMessage();
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(heart.getId());
            post.unlike();
            return CustomStatus.PostUnlikeSuccess.toMessage();
        }
    }

    // Reply 좋아요
    @Transactional
    public Message replyLike(Long replyId, User user) {

        // 1. 댓글 조회
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like like = likeRepository.findByReplyIdAndUser_Id(replyId, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, null, replyId));
            reply.like();
            return CustomStatus.ReplyLikeSuccess.toMessage();
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            reply.unlike();
            return CustomStatus.ReplyUnlikeSuccess.toMessage();
        }
    }
}
