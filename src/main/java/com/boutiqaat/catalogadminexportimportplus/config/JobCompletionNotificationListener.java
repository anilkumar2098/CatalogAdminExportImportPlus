package com.boutiqaat.catalogadminexportimportplus.config;

import com.boutiqaat.catalogadminexportimportplus.kafka.Producer;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductFlatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final CatalogProductFlatRepository catalogProductFlatRepository;

    @Autowired
    public JobCompletionNotificationListener(CatalogProductFlatRepository catalogProductFlatRepository) {
        this.catalogProductFlatRepository = catalogProductFlatRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! JOB COMPLETED! verify the results");
            catalogProductFlatRepository.findAll()
                    .forEach(exportdata -> logger.info("Found (" + exportdata + ">) in the database.") );
        }
    }
}

