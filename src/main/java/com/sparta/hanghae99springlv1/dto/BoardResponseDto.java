package com.sparta.hanghae99springlv1.dto;

import com.sparta.hanghae99springlv1.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createAt;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createAt = board.getCreateAt();
    }
}
