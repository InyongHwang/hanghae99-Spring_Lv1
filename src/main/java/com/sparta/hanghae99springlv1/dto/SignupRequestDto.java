package com.sparta.hanghae99springlv1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{4,10}$", message = "아이디는 4-10자 알파벳 소문자, 숫자로 작성해주세요.")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,15}$", message = "비밀번호는 8-15자 알파벳 대소문자, 숫자, 특수문자로 작성해주세요.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
