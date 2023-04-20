package com.sparta.hanghae99springlv1.dto;

import com.sparta.hanghae99springlv1.entity.Board;
import com.sparta.hanghae99springlv1.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private List<ReplyResponseDto> replyList;

    public BoardResponseDto(Board board, List<ReplyResponseDto> replyList) {
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.replyList = replyList;
    }
}
