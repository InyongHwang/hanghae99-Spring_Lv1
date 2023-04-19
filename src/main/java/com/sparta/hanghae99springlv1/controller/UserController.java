package com.sparta.hanghae99springlv1.controller;

import com.sparta.hanghae99springlv1.dto.LoginRequestDto;
import com.sparta.hanghae99springlv1.dto.SignupRequestDto;
import com.sparta.hanghae99springlv1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "201 Created";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "200 OK";
    }
}
