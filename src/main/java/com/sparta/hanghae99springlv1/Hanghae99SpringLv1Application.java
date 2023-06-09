package com.sparta.hanghae99springlv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Hanghae99SpringLv1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hanghae99SpringLv1Application.class, args);
	}

}
