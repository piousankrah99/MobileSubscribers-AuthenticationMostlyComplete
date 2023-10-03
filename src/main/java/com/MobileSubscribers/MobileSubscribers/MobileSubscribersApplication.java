package com.MobileSubscribers.MobileSubscribers;

import com.MobileSubscribers.MobileSubscribers.MobileSubscribers.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.MobileSubscribers.MobileSubscribers.MobileSubscribers.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@ComponentScan(basePackages = "com.MobileSubscribers") // Replace with your actual package structure
public class MobileSubscribersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileSubscribersApplication.class, args);
	}


}