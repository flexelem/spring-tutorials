package com.buraktas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class MainServiceTest {

    @Configuration
    @EnableRetry
    public static class TestConfigClass {
        @Bean
        public MainService mainService() {
            return new MainService(retryTemplate());
        }

        @Bean
        public RetryTemplate retryTemplate() {
            RetryTemplate retryTemplate = new RetryTemplate();

            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(1000L);
            retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(3);
            retryTemplate.setRetryPolicy(retryPolicy);
            return retryTemplate;
        }
    }

    @Test
    public void testProcessWithFixedDelay() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfigClass.class);
        MainService mainService = context.getBean(MainService.class);
        mainService.processRetryWithFixedDelay("foobar");
    }

    @Test
    public void testProcessWithExponentialDelay() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfigClass.class);
        MainService mainService = context.getBean(MainService.class);
        mainService.processRetryWithExponentialDelay("foobar");
    }

    @Test
    public void testProcessWithRestTemplate() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfigClass.class);
        MainService mainService = context.getBean(MainService.class);
        mainService.processRetryWithRestTemplate("foobar");
    }
}