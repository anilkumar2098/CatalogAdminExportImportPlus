package com.boutiqaat.catalogadminexportimportplus.kafka;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaJobStarter {
    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final Job job;

    public KafkaJobStarter(JobLauncher jobLauncher, JobOperator jobOperator, Job job) {
        this.jobLauncher = jobLauncher;
        this.jobOperator = jobOperator;
        this.job = job;
    }

    @KafkaListener(topics = "catalog_product_grid")
    public void startJob(ExportEvent exportEvent) {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addJobParameters(exportEvent).toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            // handle exception
        }
    }
}
