package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.service.LikeService;
import com.sparta.hanghae99springlv1.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    // Post 좋아요
    @PostMapping("/post/{postId}")
    public Message postLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.postLike(postId, userDetails.getUser());
    }

    // Comment 좋아요
    @PostMapping("/reply/{replyId}")
    public Message commentLike(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.replyLike(replyId, userDetails.getUser());
    }
}
