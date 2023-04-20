package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyUpdateDto;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    final private ReplyService replyService;

    // 댓글 작성
    @PostMapping("/reply")
    public ReplyResponseDto createReply(@RequestBody ReplyRequestDto requestDto, HttpServletRequest request) {
        // 응답 보내기
        return replyService.createReply(requestDto, request);
    }

    // 댓글 수정
    @PutMapping("/reply/{replyId}") // 댓글 id
    public ReplyResponseDto updateReply(@PathVariable Long replyId, @RequestBody String contents, HttpServletRequest request) {
        // 응답 보내기
        return replyService.updateReply(replyId, contents, request);
    }

    // 댓글 삭제
    @DeleteMapping("/reply/{replyId}")
    public Message deleteReply(@PathVariable Long replyId, HttpServletRequest request) {
        // 응답 보내기
        return replyService.deleteReply(replyId, request);
    }
}
