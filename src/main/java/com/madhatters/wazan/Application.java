package com.madhatters.wazan;

import com.madhatters.wazan.repositories.ResourceRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = ResourceRepositoryImpl.class)

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
