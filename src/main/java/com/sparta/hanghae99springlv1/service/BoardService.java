package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import com.sparta.hanghae99springlv1.dto.BoardResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.entity.UserRoleEnum;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.BoardRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

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
    public BoardResponseDto createPost(BoardRequestDto requestDto, User user) {
        // 오청받은 DTO로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user.getUsername()));

        return new BoardResponseDto(board, new ArrayList<>());
    }

    // 선택한 게시글 조회
    public BoardResponseDto viewSelectPost(Long postId) {
        Board board = boardRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        return new BoardResponseDto(board, getReplyList(postId));
    }

    // 선택한 게시글 수정
    @Transactional
    public BoardResponseDto updatePost(Long postId, BoardRequestDto requestDto, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 게시글 수정 가능
        Board board;
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(postId)
                    .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 수정 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            board = boardRepository.findByIdAndUsername(postId, user.getUsername())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
        }

        board.update(requestDto, user.getUsername());

        return new BoardResponseDto(board, getReplyList(postId));
    }

    // 게시글 삭제
    @Transactional
    public Message deletePost(Long postId, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 게시글 삭제 가능
        Board board;
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(postId)
                    .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 삭제 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            board = boardRepository.findByIdAndUsername(postId, user.getUsername())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
        }

        replyRepository.deleteAllByPostId(postId);
        boardRepository.deleteById(postId);
        return new Message("게시글 삭제 성공", 200);
    }

    private List<ReplyResponseDto> getReplyList(Long postId) {
        List<Reply> replyList = replyRepository.findAllByPostIdOrderByCreateAtDesc(postId);
        List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
        for (Reply reply : replyList) {
            replyResponseDtoList.add(new ReplyResponseDto(reply));
        }
        return replyResponseDtoList;
    }
}
