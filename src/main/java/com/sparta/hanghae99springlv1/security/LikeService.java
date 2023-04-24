package com.sparta.hanghae99springlv1.security;

import com.sparta.hanghae99springlv1.entity.Like;
import com.sparta.hanghae99springlv1.entity.Post;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
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
            return new Message("게시글 좋아요 성공", 200);
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(heart.getId());
            post.unlike();
            return new Message("게시글 좋아요 취소 성공", 200);
        }
    }

    // Reply 좋아요
    @Transactional
    public Message replyLike(Long replyId, User user) {

        // 1. 댓글 조회
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        // 2. LikeRepository 에서 postId, userId 로 조회
        Like like = likeRepository.findByReplyIdAndUser_Id(replyId, user.getId());

        // 좋아요 없으면 DB에 추가
        if (like == null) {
            likeRepository.save(new Like(user, null, replyId));
            reply.like();
            return new Message("댓글 좋아요 성공", 200);
        } else { // 좋아요 있으면 DB에서 제거
            likeRepository.deleteById(like.getId());
            reply.unlike();
            return new Message("게시글 좋아요 취소 성공", 200);
        }
    }
}
