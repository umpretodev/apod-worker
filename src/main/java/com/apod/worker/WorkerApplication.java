package com.apod.worker;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerApplication {
	@PostConstruct
	public void init() {
		System.out.println(">>>> Hello world!");
	}


	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

}
