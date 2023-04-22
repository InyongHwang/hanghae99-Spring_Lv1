package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import com.sparta.hanghae99springlv1.dto.BoardResponseDto;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.security.UserDetailsImpl;
import com.sparta.hanghae99springlv1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    // for commit
    private final BoardService boardService;

    // home page
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    // 전체 게시글 목록 조회
    @GetMapping("/posts")
    public List<BoardResponseDto> viewAllPost() {
        return boardService.viewAllPost();
    }

    // 게시글 작성
    @PostMapping("/posts")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createPost(requestDto, userDetails.getUser());
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{postId}")
    public BoardResponseDto viewSelectPost(@PathVariable Long postId) {
        return boardService.viewSelectPost(postId);
    }

    // 선택한 게시글 수정
    @PutMapping("/posts/{postId}")
    public BoardResponseDto updatePost(@PathVariable Long postId,
                                       @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updatePost(postId, requestDto, userDetails.getUser());
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public Message deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deletePost(id, userDetails.getUser());
    }
}
