package com.artogco.happy2be;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Happy2beApplication {

	public static void main(String[] args) {
		
		// Load .env variables into System properties so Spring can see them
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		
		
		SpringApplication.run(Happy2beApplication.class, args);
	}

}

