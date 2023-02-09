package com.boutiqaat.catalogadminexportimportplus.batch;


import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
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

import static com.boutiqaat.catalogadminexportimportplus.utils.SpecificationBuilder.selectFrom;

public class ExportProcessor implements ItemProcessor<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(ExportProcessor.class);

    @Autowired
    private CatalogProductFlatRepository catalogProductFlatRepository;

    private ObjectMapper jsonObjectMapper = new ObjectMapper();
    @Autowired
    private CatalogProductExportCommonService catalogProductExportCommonService;

    @Autowired
    private ExportWriter exportWriter;


    @Override
    public String process(String data) throws Exception {

        ExportEvent exportMessage = new ExportEvent();
        exportMessage.setStatus(Status.PROCESSING.getValue());
        CatalogProductExport exportInfo = jsonObjectMapper.convertValue(exportMessage.getExportInfo(),CatalogProductExport.class);
        catalogProductExportCommonService.updateRecordIntoProductExportTable(exportInfo, null, Status.PROCESSING.getValue());
        exportMessage.setProductExport(exportInfo);
        exportInfo.setUserId(exportMessage.getUserId());
        logger.debug("Product Export Request :: Started export id [{}], user id :[{}]",exportInfo.getId(),
                exportMessage.getUserId());

        Map<String, Object> filters = exportMessage.getFilters();

        if (ObjectUtils.isEmpty(filters)) {
            filters = new HashMap<>();
        }
        if (!filters.containsKey("sort")) {
            filters.put("sort", Constants.DEFAULT_SORT);
        }
        if (!filters.containsKey("direction")) {
            filters.put("direction", "desc");
        }

        addFilterForDefaultStoreId(filters);
        Pageable pageable = ControllerUtils.processPageableProperties(null, filters, true);
        String path = exportWriter.exportExcelForUpload(exportMessage.getFilters(), pageable, exportMessage.getUserId(), exportInfo,
                exportMessage.getExportType());
        if(StringUtils.isNotEmpty(path)) {
            logger.info("Product Export Request :: Finished export id [{}], user id :[{}], status :[{}]", exportInfo.getId(),
                    exportInfo.getUserId(), Status.COMPLETE.getValue());
            catalogProductExportCommonService.updateRecordIntoProductExportTable(exportInfo, path, Status.COMPLETE.getValue());
            exportMessage.setStatus(Status.COMPLETE.getValue());
        }
        return null;

    }

    public void addFilterForDefaultStoreId(Map filtersStringMap) {
        if (!filtersStringMap.containsKey("store_id")) {
            filtersStringMap.put("store_id", Integer.parseInt(String.valueOf(0)));
        }
    }



}
