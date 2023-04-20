package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import com.sparta.hanghae99springlv1.dto.BoardResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.entity.UserRoleEnum;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final JwtUtil jwtUtil;

    // 전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> viewAllPost() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardList = new ArrayList<>();
        for (Board board : boards) {
            boardList.add(new BoardResponseDto(board, getReplyList(board.getId())));
        }
        return boardList;
    }

    // 게시글 작성
    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            User user = validateTokenAndUserInfo(token);

            // 오청받은 DTO로 DB에 저장할 객체 만들기
            // 토큰을 사용하게 되면서 등록할 게시글과 username이 연관관계가 있기 때문에
            // board 생성자에 id를 추가하기 위해 board entity에 user id를 추가하였음.
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user.getUsername()));

            return new BoardResponseDto(board, new ArrayList<>());
        } else {
            return null;
        }
    }

    // 선택한 게시글 조회
    public BoardResponseDto viewSelectPost(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        return new BoardResponseDto(board, getReplyList(id));
    }

    // 선택한 게시글 수정
    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            User user = validateTokenAndUserInfo(token);

            // 사용자 권한 가져와서
            UserRoleEnum userRoleEnum = user.getRole();

            // ADMIN 이면 모든 게시글 수정 가능
            Board board;
            if (userRoleEnum == UserRoleEnum.ADMIN) {
                board = boardRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
            } else {
                // USER 이면 자기 게시글 수정 가능, 현재 유저가 작성한 게시글이 맞는지 검증
                board = boardRepository.findByIdAndUsername(id, user.getUsername())
                        .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
            }

            board.update(requestDto, user.getUsername());

            return new BoardResponseDto(board, getReplyList(id));
        } else {
            return null;
        }
    }

    @Transactional
    public String deletePost(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {
            // Token 검증 및 사용자 정보 조회 후 USER 반환
            User user = validateTokenAndUserInfo(token);

            // 사용자 권한 가져와서
            UserRoleEnum userRoleEnum = user.getRole();

            // ADMIN 이면 모든 게시글 삭제 가능
            Board board;
            if (userRoleEnum == UserRoleEnum.ADMIN) {
                board = boardRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
            } else {
                // USER 이면 자기 게시글 삭제 가능, 현재 유저가 작성한 게시글이 맞는지 검증
                board = boardRepository.findByIdAndUsername(id, user.getUsername())
                        .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
            }

            replyRepository.deleteAllByPostId(board.getId());
            boardRepository.deleteById(id);
            return "204 No Content";
        } else {
            return null;
        }
    }

    private List<ReplyResponseDto> getReplyList(Long id) {
        List<Reply> replyList = replyRepository.findAllByPostIdOrderByCreateAtDesc(id);
        List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
        for (Reply reply : replyList) {
            replyResponseDtoList.add(new ReplyResponseDto(reply));
        }
        return replyResponseDtoList;
    }

    private User validateTokenAndUserInfo(String token) {
        Claims claims;
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에 담겨있는 사용자 정보로 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return user;
    }
}
