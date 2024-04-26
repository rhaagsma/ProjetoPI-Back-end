package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@ComponentScan(
		basePackages = {
				"com.example.springboot.security",
				"com.example.springboot.controllers",
				"com.example.springboot.dtos",
				"com.example.springboot.models",
				"com.example.springboot.repositories",
				"com.example.springboot.services"

		})
@EntityScan("com.example.springboot.models")

public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
