package com.iesley.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {

		if (args.length > 0) {
			System.setProperty("spring.profiles.active", args[0]);
		}

		SpringApplication.run(TodolistApplication.class, args);
	}

}
