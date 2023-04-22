package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.security.UserDetailsImpl;
import com.sparta.hanghae99springlv1.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    final private ReplyService replyService;

    // 댓글 작성
    @PostMapping("/reply")
    public ReplyResponseDto createReply(@RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return replyService.createReply(requestDto, userDetails.getUsername());
    }

    // 댓글 수정
    @PutMapping("/reply/{replyId}")
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody String contents, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.updateReply(replyId, contents, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/reply/{replyId}")
    public Message deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.deleteReply(replyId, userDetails.getUser());
    }
}
