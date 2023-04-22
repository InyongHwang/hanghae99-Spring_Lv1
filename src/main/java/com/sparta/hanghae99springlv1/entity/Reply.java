package com.sparta.hanghae99springlv1.entity;

import com.sparta.hanghae99springlv1.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private Long postId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String contents;

    public Reply(ReplyRequestDto requestDto, User user) {
        this.postId = requestDto.getPostId();
        this.user = user;
        this.contents = requestDto.getContents();
    }

    public void update(ReplyRequestDto requestDto, User user) {
        this.postId = requestDto.getPostId();
        this.user = user;
        this.contents = requestDto.getContents();
    }
}