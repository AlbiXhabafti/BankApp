package com.example.BankingApp;

import com.example.BankingApp.user.model.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "BankApp APIS",version = "1.0", description = "Bank Management Apis"))
public class BankingAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

}
