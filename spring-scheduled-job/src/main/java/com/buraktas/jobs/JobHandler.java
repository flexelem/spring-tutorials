package com.buraktas.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobHandler {
    Logger logger = LoggerFactory.getLogger(JobHandler.class);

    @Scheduled(initialDelay = 3000L, fixedRate = 200L)
    public void jobWithFixedRate() throws InterruptedException {
        logger.info("Hi from jobWithFixedRate");
    }

    @Scheduled(initialDelay = 1000L, fixedRate = 300L)
    public void jobWithFixedRate_2() {
        logger.info("Hi from jobWithFixedRate_2");
    }

    @Scheduled(initialDelay = 2000L, fixedRate = 1000L)
    public void jobWithFixedRate_3() {
        logger.info("Hi from jobWithFixedRate_3");
    }
}
