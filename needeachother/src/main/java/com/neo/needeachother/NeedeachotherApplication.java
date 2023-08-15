package com.neo.needeachother;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NeedeachotherApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeedeachotherApplication.class, args);
	}

}
