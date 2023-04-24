package com.sparta.hanghae99springlv1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "Likes")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private Long postId;

    private Long replyId;

    public Like(User user, Long postId, Long replyId) {
        this.user = user;
        this.postId = postId;
        this.replyId = replyId;
    }
}
