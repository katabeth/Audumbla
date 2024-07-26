package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.User;
import com.sparta.audumbla.audumblaworldjpa.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AudumblaWorldJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudumblaWorldJpaApplication.class, args);
	}
	@Bean
	CommandLineRunner runner (UsersRepository usersRepository, PasswordEncoder passwordEncoder){

		return args -> {
			if (usersRepository.findByUsername("admin").isEmpty()) {
				usersRepository.save(new User("admin", passwordEncoder.encode("password"),"ROLE_ADMIN"));
			}

			if (usersRepository.findByUsername("user").isEmpty()) {
				usersRepository.save(new User("user", passwordEncoder.encode("password"),"ROLE_USER"));
			}

		};
	}
}
