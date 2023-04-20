package com.sparta.hanghae99springlv1.dto;

import lombok.Getter;

@Getter
public class ReplyRequestDto {
    private Long postId;
    private String contents;

    public ReplyRequestDto(Long postId, String contents) {
        this.postId = postId;
        this.contents = contents;
    }
}
