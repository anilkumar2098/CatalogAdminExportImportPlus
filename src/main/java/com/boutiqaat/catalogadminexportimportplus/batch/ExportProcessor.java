package com.boutiqaat.catalogadminexportimportplus.batch;


import com.boutiqaat.catalogadminexportimportplus.common.ListingResponse;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlatService;
import com.boutiqaat.catalogadminexportimportplus.domain.ProductSearchService;
import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;
import com.boutiqaat.catalogadminexportimportplus.kafka.ExportEvent;
import com.boutiqaat.catalogadminexportimportplus.model.Constants;
import com.boutiqaat.catalogadminexportimportplus.model.Filter;
import com.boutiqaat.catalogadminexportimportplus.model.Status;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductFlatRepository;
import com.boutiqaat.catalogadminexportimportplus.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import static com.boutiqaat.catalogadminexportimportplus.model.Constants.EXPORT_PAGE_SIZE;
import static com.boutiqaat.catalogadminexportimportplus.utils.SpecificationBuilder.selectFrom;

public class ExportProcessor implements ItemProcessor<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(ExportProcessor.class);

    @Autowired
    private CatalogProductFlatService catalogProductFlatService;

    ExportEvent exportEvent;

    @Override
    public String process(String data) throws Exception {
        data = catalogProductFlatService.processListingExport(exportEvent);
        return data;
    }





}
