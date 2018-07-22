package com.ahkamel.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * Application Entry point
 * @author Ahmed Kamel
 *
 */
//Scan all my packages as it is just small application, otherwise I'll split the components in more than one configuration file and scan from their
@ComponentScan({ "com.ahkamel" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
