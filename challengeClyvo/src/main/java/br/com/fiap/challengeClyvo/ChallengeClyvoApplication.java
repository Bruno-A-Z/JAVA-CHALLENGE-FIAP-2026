package br.com.fiap.challengeClyvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ChallengeClyvoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ChallengeClyvoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeClyvoApplication.class, args);
	}

}
