package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.entity.UserRoleEnum;
import com.sparta.hanghae99springlv1.jwt.JwtUtil;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.BoardRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import com.sparta.hanghae99springlv1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    @Transactional
    public ReplyResponseDto createReply(ReplyRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에 담겨있는 사용자 정보로 DB 조회
            User user = userRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

            // 게시글 id로 DB 조회
            Board board = boardRepository.findById(requestDto.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

            // 오청받은 DTO로 DB에 저장할 객체 만들기
            Reply reply = replyRepository.saveAndFlush(new Reply(requestDto, user));

            return new ReplyResponseDto(reply);
        } else {
            return null;
        }
    }

    // 댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long replyId, String contents, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 수정 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에 담겨있는 사용자 정보로 DB 조회
            User user = userRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

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

            contents = contents.substring(20, contents.length()-4);
            reply.update(new ReplyRequestDto(reply.getPostId(), contents), user);

            return new ReplyResponseDto(reply);
        } else {
            return null;
        }
    }

    // 댓글 삭제
    @Transactional
    public Message deleteReply(Long replyId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 수정 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에 담겨있는 사용자 정보로 DB 조회
            User user = userRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

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
        } else {
            return null;
        }
    }
}
