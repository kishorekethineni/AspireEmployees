package com.aspiresys.AspireEmployees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aspiresys.AspireEmployees"})
public class AspireEmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AspireEmployeesApplication.class, args);
	}

}
