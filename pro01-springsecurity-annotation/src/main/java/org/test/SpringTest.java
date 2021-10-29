package org.test;

import org.entity.Employee;
import org.example.MyAnnotationConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {
    @Test
    public void test01()
    {
       AnnotationConfigApplicationContext annotationConfigApplicationContext=
               new AnnotationConfigApplicationContext(MyAnnotationConfiguration.class);
        Employee employee  = annotationConfigApplicationContext.getBean(Employee.class);
        System.out.println(employee);
    }
}
