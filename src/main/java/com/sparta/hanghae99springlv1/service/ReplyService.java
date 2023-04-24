package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.*;
import com.sparta.hanghae99springlv1.jwt.JwtUtil;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.PostRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import com.sparta.hanghae99springlv1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public ReplyResponseDto createReply(Long postId, ReplyRequestDto requestDto, User user) {

        // 게시글 id로 DB 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 오청받은 DTO로 DB에 저장할 객체 만들기
        Reply reply = replyRepository.saveAndFlush(new Reply(requestDto, post, user));

        return new ReplyResponseDto(reply);
    }

    // 댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long replyId, ReplyRequestDto requestDto, User user) {
        // 게시글이 존재하는지 확인
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 사용자 권한이 USER이고, 자기가 작성한 댓글이 아니라면 오류 발생
        if (user.getRole() == UserRoleEnum.USER && !reply.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("현재 사용자가 작성한 댓글이 아닙니다.");
        }

        reply.update(requestDto);
        return new ReplyResponseDto(reply);
    }

    // 댓글 삭제
    @Transactional
    public Message deleteReply(Long replyId, User user) {
        // 게시글이 존재하는지 확인
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 사용자 권한이 USER이고, 자기가 작성한 댓글이 아니라면 오류 발생
        if (user.getRole() == UserRoleEnum.USER && !reply.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("현재 사용자가 작성한 댓글이 아닙니다.");
        }

        replyRepository.deleteById(replyId);
        return CustomStatus.DeleteReplySuccess.toMessage();
    }
}