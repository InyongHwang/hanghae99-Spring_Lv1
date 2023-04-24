package com.sparta.hanghae99springlv1.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String msg;
    private int statusCode;
}
