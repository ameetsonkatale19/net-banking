package com.nationalbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.nationalbank.repository")
public class NetBankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetBankingAppApplication.class, args);
	}

}
