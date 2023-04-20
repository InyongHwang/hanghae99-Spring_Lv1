package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    final private ReplyService replyService;

    // 댓글 작성
    @PostMapping("/reply")
    public ReplyResponseDto createReply(
            @RequestBody ReplyRequestDto requestDto, // 게시글 id, 댓글 내용
            HttpServletRequest request // jwt 인증
    ) {
        // 응답 보내기
        return replyService.createReply(requestDto, request);
    }

    // 댓글 수정
//    @PutMapping("/reply/{id}")

    // 댓글 삭제
//    @DeleteMapping("/reply{id}")
}
