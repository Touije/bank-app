package com.example.compteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.bank.compte")
@EntityScan("com.bank.compte.entities")
@EnableJpaRepositories("com.bank.compte.repository")
public class CompteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompteServiceApplication.class, args);
	}

}
