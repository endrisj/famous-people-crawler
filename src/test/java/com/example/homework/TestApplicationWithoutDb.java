package com.example.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class TestApplicationWithoutDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplicationWithoutDb.class, args);
    }
}
