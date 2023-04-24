package com.sparta.hanghae99springlv1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{4,10}$")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,15}$")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
