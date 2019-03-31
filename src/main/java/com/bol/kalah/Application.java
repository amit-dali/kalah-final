package com.bol.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application entry point or start-up class.
 * 
 * @author AMDALI
 *
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }
}
