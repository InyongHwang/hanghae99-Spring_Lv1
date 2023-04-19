package com.sparta.hanghae99springlv1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 10, message = "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자와 숫자로 구성되어야 합니다.")
    @Pattern(regexp = "[a-z0-9]]", message = "아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자와 숫자로 구성되어야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자와 숫자로 구성되어야 합니다.")
    @Pattern(regexp = "[a-zA-Z0-9]]")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
