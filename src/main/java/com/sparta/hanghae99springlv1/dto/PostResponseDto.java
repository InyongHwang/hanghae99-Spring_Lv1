package com.sparta.hanghae99springlv1.dto;

import com.sparta.hanghae99springlv1.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    // for commit
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private List<ReplyResponseDto> replyList;

    public PostResponseDto(Post post, List<ReplyResponseDto> replyList) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.username = post.getUser().getUsername();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.replyList = replyList;
    }
}
