package com.example.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;

@SpringBootApplication(exclude = {
//    DataSourceAutoConfiguration.class,
//    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class//,
//    AutoConfigureTestDatabase.class //,
//    JpaRepositoriesAutoConfiguration.class,
//    SpringDataWebAutoConfiguration.class,
//    TransactionAutoConfiguration.class
})
public class TestApplicationWithoutDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplicationWithoutDb.class, args);
    }
}
