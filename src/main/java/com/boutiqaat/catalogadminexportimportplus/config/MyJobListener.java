package com.boutiqaat.catalogadminexportimportplus.config;

import com.boutiqaat.catalogadminexportimportplus.batch.ExportProcessor;
import com.boutiqaat.catalogadminexportimportplus.domain.FilterProcessor;
import com.boutiqaat.catalogadminexportimportplus.domain.RequestData;
import com.boutiqaat.catalogadminexportimportplus.domain.SearchProductRequestDto;
import com.boutiqaat.catalogadminexportimportplus.kafka.ExportEvent;
import com.boutiqaat.catalogadminexportimportplus.model.*;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductFlatRepository;
import com.boutiqaat.catalogadminexportimportplus.utils.CatalogProductUtils;
import com.boutiqaat.catalogadminexportimportplus.utils.UserConfigurationUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

public class MyJobListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(MyJobListener.class);

    @Autowired
    private FilterProcessor filterProcessor;

    @Autowired
    private CatalogProductFlatRepository catalogProductFlatRepository;


    Map<String, Object> filtersMap;
    Pageable pageable;

    Map<String, Object> filters;



    @Override
    public void beforeJob(JobExecution jobExecution) {
        filterProcessor.processFilters(filtersMap, pageable);
        //filterProcessor.fetchSavedFilters(filters);

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
