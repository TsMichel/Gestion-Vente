package com.example.venteinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VenteinfoApplication {
	public static void main(String[] args) {
		SpringApplication.run(VenteinfoApplication.class, args);
	}
}