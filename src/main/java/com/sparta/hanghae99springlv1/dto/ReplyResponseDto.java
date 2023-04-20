package com.sparta.hanghae99springlv1.dto;

import com.sparta.hanghae99springlv1.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String username;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.createAt = reply.getCreateAt();
        this.modifiedAt = reply.getModifiedAt();
        this.username = reply.getUser().getUsername();
    }
}
