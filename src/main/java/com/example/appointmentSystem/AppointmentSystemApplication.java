package com.example.appointmentSystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Appoint System Apis", version = "v1" ))
public class AppointmentSystemApplication {


	private ModelMapper modelMapper;

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}
}
