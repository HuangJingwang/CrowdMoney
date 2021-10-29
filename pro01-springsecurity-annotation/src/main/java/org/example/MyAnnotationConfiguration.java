package org.example;


import org.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAnnotationConfiguration {

    @Bean
    public Employee getEmployee()
    {
        return new Employee();
    }
}
