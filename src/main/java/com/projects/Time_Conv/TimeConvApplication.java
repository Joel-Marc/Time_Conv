package com.projects.Time_Conv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeConvApplication {

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(TimeConvApplication.class, args);
		Toke.testToke("7 am IST to PST");
		Toke.testToke("PST of 7 am IST");

	}

}
