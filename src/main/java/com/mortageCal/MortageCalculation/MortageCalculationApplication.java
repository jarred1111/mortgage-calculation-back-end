package com.mortageCal.MortageCalculation;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mortageCal.MortageCalculation.entities.MortageInputs;
import com.mortageCal.MortageCalculation.theBrain.Calculate;

@SpringBootApplication
public class MortageCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortageCalculationApplication.class, args);
		
		//Quick configuration for the sl4j logger
		BasicConfigurator.configure();
	}
}
