package com.sparta.hanghae99springlv1.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
