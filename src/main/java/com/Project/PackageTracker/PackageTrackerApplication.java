package com.Project.PackageTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PackageTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackageTrackerApplication.class, args);
	}

}
