package com.ext.teros.message_connector.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

//@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Executor exec = new Executor();
		exec.loadConfig("test");
		exec.initialize();
		exec.setData("hello");
		exec.output();
	}
}
