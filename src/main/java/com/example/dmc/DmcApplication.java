package com.example.dmc;

import com.example.dmc.config.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitConfig.class)
public class DmcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmcApplication.class, args);
	}

}
