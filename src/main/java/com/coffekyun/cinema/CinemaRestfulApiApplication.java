package com.coffekyun.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication()
public class CinemaRestfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaRestfulApiApplication.class, args);
	}

}
