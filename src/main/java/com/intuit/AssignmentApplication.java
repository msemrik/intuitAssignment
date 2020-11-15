package com.intuit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AssignmentApplication {


//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer customizeJson()
//    {
//        return builder -> {
//
//            builder.indentOutput(true);
//            builder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
//        };
//    }

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}
