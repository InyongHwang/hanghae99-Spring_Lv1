package com.sparta.hanghae99springlv1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghae99springlv1.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    //@JsonIgnore
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    public Board(BoardRequestDto requestDto) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

    public void update(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
