package com.sparta.hanghae99springlv1.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ReplyRequestDto {
    private String contents;

    public ReplyRequestDto(String contents) {
        this.contents = contents;
    }
}