package com.sparta.hanghae99springlv1.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.sparta.hanghae99springlv1.message.Message;

public enum CustomStatus {
    LoginSuccess("로그인 성공", 200),
    SignupSuccess("회원가입 성공", 200),
    DeleteReplySuccess("댓글 삭제 성공", 200),
    DeletePostSuccess("게시글 삭제 성공", 200),
    PostLikeSuccess("게시글 좋아요 성공", 200),
    PostUnlikeSuccess("게시글 좋아요 취소 성공", 200),
    ReplyLikeSuccess("댓글 좋아요 성공", 200),
    ReplyUnlikeSuccess("댓글 좋아요 취소 성공", 200);

    private final String reasonPhrase;

    private final int value;

    CustomStatus(String reasonPhrase, int value) {
        this.reasonPhrase = reasonPhrase;
        this.value = value;
    }

    public Message toMessage() {
        return new Message(this.reasonPhrase, this.value);
    }
}
