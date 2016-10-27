package com.example.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.AuditAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.CacheStatisticsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.CrshAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.InfoContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.JolokiaAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementServerPropertiesAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricExportAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricsChannelAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.PublicMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceRepositoryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceWebFilterAutoConfiguration;

@SpringBootApplication(exclude = {
    HibernateJpaAutoConfiguration.class,
    AuditAutoConfiguration.class,
    CacheStatisticsAutoConfiguration.class,
    CrshAutoConfiguration.class,
    EndpointAutoConfiguration.class,
    EndpointMBeanExportAutoConfiguration.class,
    EndpointWebMvcAutoConfiguration.class,
    HealthIndicatorAutoConfiguration.class,
    InfoContributorAutoConfiguration.class,
    JolokiaAutoConfiguration.class,
    ManagementServerPropertiesAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class,
    MetricExportAutoConfiguration.class,
    MetricFilterAutoConfiguration.class,
    MetricRepositoryAutoConfiguration.class,
    MetricsChannelAutoConfiguration.class,
    MetricsDropwizardAutoConfiguration.class,
    PublicMetricsAutoConfiguration.class,
    TraceRepositoryAutoConfiguration.class,
    TraceWebFilterAutoConfiguration.class
})
public class TestApplicationWithoutDb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplicationWithoutDb.class, args);
    }
}
