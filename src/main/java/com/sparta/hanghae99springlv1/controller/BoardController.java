package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import com.sparta.hanghae99springlv1.dto.BoardResponseDto;
import com.sparta.hanghae99springlv1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

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
    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        // 응답 보내기
        return boardService.createPost(requestDto, request);
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{id}")
    public BoardResponseDto viewSelectPost(@PathVariable Long id) {
        // 응답 보내기
        return boardService.viewSelectPost(id);
    }

    // 선택한 게시글 수정
    @PutMapping("/posts/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id,
                                       @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.updatePost(id, requestDto, request);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deletePost(id, request);
    }
}
