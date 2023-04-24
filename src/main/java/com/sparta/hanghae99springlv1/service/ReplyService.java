package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Post;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.entity.UserRoleEnum;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    @Transactional
    public ReplyResponseDto createReply(Long postId, ReplyRequestDto requestDto, User user) {

        // 게시글 id로 DB 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 오청받은 DTO로 DB에 저장할 객체 만들기
        Reply reply = replyRepository.saveAndFlush(new Reply(requestDto, post, user));

        return new ReplyResponseDto(reply);
    }

    // 댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long replyId, ReplyRequestDto requestDto, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 댓글 수정 가능
        Reply reply;
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            reply = replyRepository.findById(replyId)
                    .orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 수정 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            reply = replyRepository.findByIdAndUser_Id(replyId, user.getId())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 댓글이 아닙니다."));
        }

        reply.update(requestDto);

        return new ReplyResponseDto(reply);
    }

    // 댓글 삭제
    @Transactional
    public Message deleteReply(Long replyId, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 댓글 삭제 가능
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            Reply reply = replyRepository.findById(replyId)
                    .orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 삭제 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            Reply reply = replyRepository.findByIdAndUser_Id(replyId, user.getId())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 댓글이 아닙니다."));
        }

        replyRepository.deleteById(replyId);
        return new Message("댓글 삭제 성공", 200);
    }
}