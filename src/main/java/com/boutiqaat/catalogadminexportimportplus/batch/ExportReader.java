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
import org.apache.commons.lang3.tuple.Triple;
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

    @Autowired
    private CatalogProductFlatHelper catalogProductFlatHelper;

    @Autowired
    private CatalogProductFlatCacheHelper catalogProductFlatCacheHelper;

    private ObjectMapper jsonObjectMapper = new ObjectMapper();
    @Autowired
    private CatalogProductExportCommonService catalogProductExportCommonService;


    @Override
    public String process(String data) throws Exception {

        ExportEvent exportMessage = (ExportEvent) data;
        exportMessage.setStatus(Status.PROCESSING.getValue());
        CatalogProductExport exportInfo = jsonObjectMapper.convertValue(exportMessage.getExportInfo(),CatalogProductExport.class);
        catalogProductExportCommonService.updateRecordIntoProductExportTable(exportInfo, null, Status.PROCESSING.getValue());
        exportMessage.setProductExport(exportInfo);
        exportInfo.setUserId(exportMessage.getUserId());
        logger.debug("Product Export Request :: Started export id [{}], user id :[{}]",exportInfo.getId(),
                exportMessage.getUserId());

        Map<String, Object> filters=exportMessage.getFilters();

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
        String path = exportExcelForUpload(exportMessage.getFilters(), pageable, exportMessage.getUserId(),exportInfo);
        if(StringUtils.isNotEmpty(path)) {
            logger.info("Product Export Request :: Finished export id [{}], user id :[{}], status :[{}]", exportInfo.getId(),
                    exportInfo.getUserId(), Status.COMPLETE.getValue());
            catalogProductExportCommonService.updateRecordIntoProductExportTable(exportInfo, path, Status.COMPLETE.getValue());
            exportMessage.setStatus(Status.COMPLETE.getValue());
        }
        return null;

    }

    public String exportExcelForUpload(Map<String, Object> allRequestParams, Pageable pageable, Long userId, CatalogProductExport catalogProductExport) {
        try {
            // Prepare CSV file for upload
            Map<String,String> derivedColumns = new HashMap<>();
            derivedColumns.put("childSkus","Child Skus");
            List<Map<String,String>> derivedColumnList = Arrays.asList(derivedColumns);
            final Map<String, String> columnMappings = catalogProductFlatCacheHelper.getColumnMappingsForCsv(derivedColumnList);
            Map<String, String> orderedColumnMappings = new LinkedHashMap<>(100);
            Set<String> columnSet = new LinkedHashSet<>(100);
            columnSet.add("product_id");
            if (allRequestParams.containsKey("columns")) {
                Object obj = allRequestParams.get("columns");
                if (obj instanceof String) {
                    String columnsStr = (String) obj;
                    columnSet.addAll(Arrays.asList(columnsStr.split(",")));
                } else if (obj instanceof List<?>) {
                    columnSet.addAll((List<String>) obj);
                }
                columnSet.parallelStream().forEachOrdered(key -> {
                    if(StringUtils.isNotEmpty(key))
                        orderedColumnMappings.put(key, columnMappings.get(key));
                });
                allRequestParams.remove("columns");
            }
            //Triple<String, Filter, List<?>> values = processFilters(allRequestParams);
           // Filter filter = values.getMiddle();
            logger.info("Product Export Request :: Data Fetching for export id: [{}]", catalogProductExport.getId());
            List<Map<String, Object>> valueMap = (List<Map<String, Object>>) this.transformResultBeforeSending(pageable, null, null, null)
                    .getContent();
            logger.info("Product Export Request :: Data Fetched for export id: [{}]", catalogProductExport.getId());
            List<Map<String, Object>> valueMapAccordingToColumnMappings = new ArrayList<>(200000);
            valueMap.stream().forEach(mapOfValues -> {
                Map<String, Object> orderedValueMap = new LinkedHashMap<>(100);
                orderedColumnMappings.entrySet().stream().forEach(entry -> {
                    if(ObjectUtils.isNotEmpty(entry.getKey()))
                        orderedValueMap.put(entry.getKey(), mapOfValues.get(entry.getKey()));
                });
                valueMapAccordingToColumnMappings.add(orderedValueMap);
            });
            // create destination directory if not present else throw IOException
            logger.info("Product Export Request :: Data prepared for export id: [{}]", catalogProductExport.getId());
            String fullDirectoryString = Constants.EXPORT_UPLOAD_FOLDER + File.separator + userId;
            Path fullDirectoryPath = Paths.get(fullDirectoryString).toAbsolutePath().normalize();
            Files.createDirectories(fullDirectoryPath.toAbsolutePath());
            File excelFile = new File(fullDirectoryPath.toAbsolutePath() + File.separator + Constants.FILE_NAME_PREFIX
                    + Constants.DATE_EXCEL_FORMATTER.format(LocalDateTime.now()) + "_" + catalogProductExport.getId() + ".xlsx");
            logger.debug("Product Export Request :: started Excel export id :[{}], user id: [{}] processing with file" +
                            " {}, created in Path {}",
                    catalogProductExport.getId(), catalogProductExport.getUserId(), excelFile, fullDirectoryPath);
            String physicalPath = CatalogProductUtils.exportWriter.writeItems(excelFile, orderedColumnMappings, valueMapAccordingToColumnMappings);
            return FilenameUtils.separatorsToUnix(physicalPath.substring(physicalPath.indexOf("export")));
        } catch (Exception e) {
            logger.error("Product Export Request :: exception found for export id :[{}], exception: ", catalogProductExport.getId(), e);
            catalogProductExportCommonService.updateRecordIntoProductExportTable(catalogProductExport, null, Status.FAILED.getValue());
        }
        return null;

    }

    private Page<Map<String, Object>> transformResultBeforeSending(Pageable pageable, Filter filter, Integer count,
                                                                   Set<String> columnNames) {
        SpecificationBuilder<CatalogProductFlat> builder = selectFrom(catalogProductFlatRepository).where(filter);
        Page<CatalogProductFlat> retValue = catalogProductFlatRepository.findAll(
                (Specification<CatalogProductFlat>) builder.getSpecification(), pageable, count,
                CatalogProductFlat.class, columnNames);
        return catalogProductFlatHelper.transformResultForOptionValues(retValue);
    }

    public void addFilterForDefaultStoreId(Map filtersStringMap) {
        if (!filtersStringMap.containsKey("store_id")) {
            filtersStringMap.put("store_id", Integer.parseInt(String.valueOf(0)));
        }
    }



}
