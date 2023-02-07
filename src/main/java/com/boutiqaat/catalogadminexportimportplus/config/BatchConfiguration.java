package com.boutiqaat.catalogadminexportimportplus.config;

import com.boutiqaat.catalogadminexportimportplus.batch.ExportReader;
import com.boutiqaat.catalogadminexportimportplus.batch.ExportWriter;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.batch.ExportProcessor;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductFlatRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobRepository jobRepository;

    @Value("${file.input}")
    private CatalogProductFlat catalogProductExport;

    @Autowired
    @Lazy
    public CatalogProductFlatRepository catalogProductFlatRepository;





    @Bean
    public Job createJob() {
        return jobBuilderFactory.get("MyJob")
                .incrementer(new RunIdIncrementer())
                .flow(createStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("MyStep")
                .<String, String> chunk(4000)
                .reader(new ExportReader())
                .processor(new ExportProcessor())
                .writer((ItemWriter<? super String>) new ExportWriter())
                .build();
    }
}

