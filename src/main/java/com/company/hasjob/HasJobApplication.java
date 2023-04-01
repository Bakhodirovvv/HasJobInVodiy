package com.company.hasjob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class HasJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(HasJobApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                if (beanName.equals("regionRepository") || beanName.equals("districtRepository")){
//                    System.out.println(beanName);
                }
            }
            System.out.println("Length: " + beanNames.length);
        };
    }

}
