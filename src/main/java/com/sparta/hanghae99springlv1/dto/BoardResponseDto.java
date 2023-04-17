package com.sparta.hanghae99springlv1.dto;

import com.sparta.hanghae99springlv1.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private String username;
    private String title;
    private String contents;

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
    }
}
