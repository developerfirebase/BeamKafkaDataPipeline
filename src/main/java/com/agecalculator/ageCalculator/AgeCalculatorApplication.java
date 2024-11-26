package com.agecalculator.ageCalculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AgeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgeCalculatorApplication.class, args);
		log.info("Age Calculator is on \uD83D\uDE08");
	}

}
