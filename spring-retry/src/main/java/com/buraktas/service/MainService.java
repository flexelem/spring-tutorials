package com.buraktas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainService.class);

    private final RetryTemplate retryTemplate;

    @Autowired
    MainService(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff (delay = 1000L),
            value = {
                    RuntimeException.class
            }
    )
    public void processRetryWithFixedDelay(String requestId) {
        LOGGER.info("Processing request {}", requestId);
        throw new RuntimeException("Failed operation");
    }

    @Retryable(
            maxAttempts = 4,
            backoff = @Backoff (delay = 1000L, multiplier = 2),
            value = {
                    RuntimeException.class
            }
    )
    public void processRetryWithExponentialDelay(String requestId) {
        LOGGER.info("Processing request {}", requestId);
        throw new RuntimeException("Failed operation");
    }

    @Recover
    public void recover(RuntimeException ex, String requestId) {
        LOGGER.info("Recovering request {}", requestId);
    }

    public void processRetryWithRestTemplate(String requestId) {
        LOGGER.info("Processing request {}", requestId);
        retryTemplate.execute(new ProcessCallback(), new ProcessRecoveryCallback());
    }

    private class ProcessCallback implements RetryCallback<Boolean, RuntimeException> {

        @Override
        public Boolean doWithRetry(RetryContext context) throws RuntimeException {
            LOGGER.info("Processing request...");
            throw new RuntimeException("Failed operation");
        }
    }

    private class ProcessRecoveryCallback implements RecoveryCallback<Boolean> {

        @Override
        public Boolean recover(RetryContext context) {
            LOGGER.info("Recovering request...");
            return true;
        }
    }
}
