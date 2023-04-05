package com.stackfortech.testWithCoverage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestWithCoverageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWithCoverageApplication.class, args);
		System.out.println("java_vers: "+System.getProperty("java.version"));
	}

}
