package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.PostRequestDto;
import com.sparta.hanghae99springlv1.dto.PostResponseDto;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.security.UserDetailsImpl;
import com.sparta.hanghae99springlv1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // home page
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    // 전체 게시글 목록 조회
    @GetMapping("/posts")
    public List<PostResponseDto> viewAllPost() {
        return postService.viewAllPost();
    }

    // 게시글 작성
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 선택한 게시글 조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto viewSelectPost(@PathVariable Long postId) {
        return postService.viewSelectPost(postId);
    }

    // 선택한 게시글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId,
                                      @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, requestDto, userDetails.getUser());
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public Message deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}
