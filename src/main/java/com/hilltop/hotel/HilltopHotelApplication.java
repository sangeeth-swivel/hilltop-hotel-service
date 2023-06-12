package com.hilltop.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class HilltopHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HilltopHotelApplication.class, args);
	}

}
