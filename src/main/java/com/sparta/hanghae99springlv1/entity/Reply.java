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
    @Column(name = "replyId")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int replyLike;

    @JoinColumn(nullable = false, name = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(nullable = false, name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Reply(ReplyRequestDto requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.post = post;
        this.user = user;
        this.replyLike = 0;
    }

    public void update(ReplyRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }

    public void like() {
        this.replyLike += 1;
    }

    public void unlike() {
        this.replyLike -= 1;
    }
    //    public Reply(ReplyRequestDto requestDto, User user) {
//        this.postId = requestDto.getPostId();
//        this.user = user;
//        this.contents = requestDto.getContents();
//    }

//    public void update(ReplyRequestDto requestDto, User user) {
//        this.postId = requestDto.getPostId();
//        this.user = user;
//        this.contents = requestDto.getContents();
//    }
}