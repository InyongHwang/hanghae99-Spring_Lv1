package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import com.sparta.hanghae99springlv1.dto.BoardResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.jwt.JwtUtil;
import com.sparta.hanghae99springlv1.repository.BoardRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import com.sparta.hanghae99springlv1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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
}
