package com.boutiqaat.catalogadminexportimportplus.batch;

import com.boutiqaat.catalogadminexportimportplus.common.Constant;
import com.boutiqaat.catalogadminexportimportplus.common.ListingResponse;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;
import com.boutiqaat.catalogadminexportimportplus.model.Constants;
import com.boutiqaat.catalogadminexportimportplus.model.Status;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductFlatRepository;
import com.boutiqaat.catalogadminexportimportplus.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.batch.api.chunk.ItemWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.boutiqaat.catalogadminexportimportplus.model.Constants.EXPORT_PAGE_SIZE;

public class ExportWriter implements ItemWriter {

    private static final Logger logger = LoggerFactory.getLogger(ExportWriter.class);

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    private CatalogProductFlatRepository catalogProductFlatRepository;



    @Autowired
    public CatalogProductFlat catalogProductFlat;

    private ObjectMapper jsonObjectMapper = new ObjectMapper();
    @Autowired
    private CatalogProductExportCommonService catalogProductExportCommonService;


    @Override
    public void open(Serializable serializable) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void writeItems(List<Object> list) throws Exception {

    }

    public String exportExcelForUpload(Map<String, Object> allRequestParams, Pageable pageable, Long userId,
                                       CatalogProductExport catalogProductExport, String exportType) {
        try {
            // Prepare CSV file for upload

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
                    if (StringUtils.isNotEmpty(key)) {
                        // logger.info("orderedColumnMappings:: key: [{}], value: [{}]", key,
                        // columnMappings.get(key));

                        // special case for product prices:
                        // ================================
                        switch (key) {
                            case "price":
                                orderedColumnMappings.put(key, "kw price");
                                break;
                            case "special_price":
                                orderedColumnMappings.put(key, "kw special price");
                                break;
                            case "quantity_and_stock_status":
                                orderedColumnMappings.put(key, "kw quantity");
                                break;
                            case "ksa_price":
                                orderedColumnMappings.put(key, "ksa price");
                                break;
                            case "ksa_special_price":
                                orderedColumnMappings.put(key, "ksa special price");
                                break;
                            case "quantity_ksa":
                                orderedColumnMappings.put(key, "ksa quantity");
                                break;
                            default:
                                orderedColumnMappings.put(key, key.replaceAll("_", " "));
                                break;
                        }
                    }
                });
                allRequestParams.remove("columns");
            }

            List<Map<String, Object>> valueMap = new ArrayList<>();

            logger.debug("Product Export Request (Octopus):: Data Fetching for export id: [{}]",
                    catalogProductExport.getId());

            List<String> includeSource = new ArrayList<>(Arrays.asList("id", "sku", "name",
                    "attributes.attribute_set","attributes.gender","attributes.thumbnail"
                    ,"attributes.image", "status", "stocks", "type", "categories", "manufacturer"
                    , "relations.configs", "relations.bundle_options"));

            if(pageable.getPageSize() > EXPORT_PAGE_SIZE) {
                allRequestParams.put("include_source", includeSource);
                for(int page = 1; page * EXPORT_PAGE_SIZE <= pageable.getPageSize(); page++){
                    Pageable smallPageable = processPageableForSplitData(EXPORT_PAGE_SIZE, page);
                    allRequestParams = this.processFilter(allRequestParams, smallPageable);
                    List<Map<String, Object>> result = (List<Map<String, Object>>) ((ListingResponse<?>) productSearchService.searchProducts(allRequestParams, pageable, allRequestParams.containsKey("sku"), Constants.SEARCH_TYPE_LISTING)).getData();
                    if(result.isEmpty()){
                        break;
                    }
                    valueMap.addAll(result);
                }
            }else{
                allRequestParams = this.processFilter(allRequestParams, pageable);
                List<Map<String, Object>> result = (List<Map<String, Object>>) ((ListingResponse<?>) productSearchService.searchProducts(allRequestParams, pageable, allRequestParams.containsKey("sku"), Constants.SEARCH_TYPE_LISTING)).getData();
                valueMap.addAll(result);
            }

            logger.debug("Product Export Request :: Data Fetched for export id: [{}]", catalogProductExport.getId());
            List<Map<String, Object>> valueMapAccordingToColumnMappings = new ArrayList<>(200000);
            valueMap.stream().forEach(mapOfValues -> {
                Map<String, Object> orderedValueMap = new LinkedHashMap<>(100);
                orderedColumnMappings.entrySet().stream().forEach(entry -> {
                    if (ObjectUtils.isNotEmpty(entry.getKey())) {
                        // logger.info("orderedValueMap:: key: [{}], value: [{}]", entry.getKey(),
                        // mapOfValues.get(entry.getKey()));
                        orderedValueMap.put(entry.getKey(), mapOfValues.get(entry.getKey()));
                    }
                });
                valueMapAccordingToColumnMappings.add(orderedValueMap);
            });
            // create destination directory if not present else throw IOException
            logger.debug("Product Export Request :: Data prepared for export id: [{}]", catalogProductExport.getId());
            String fullDirectoryString = Constants.EXPORT_UPLOAD_FOLDER + File.separator + userId;
            Path fullDirectoryPath = Paths.get(fullDirectoryString).toAbsolutePath().normalize();
            Files.createDirectories(fullDirectoryPath.toAbsolutePath());
            File excelFile = new File(fullDirectoryPath.toAbsolutePath() + File.separator + Constants.FILE_NAME_PREFIX
                    + Constants.DATE_EXCEL_FORMATTER.format(LocalDateTime.now()) + "_" + catalogProductExport.getId()
                    + ".xlsx");
            logger.debug(
                    "Product Export Request :: started Excel export id :[{}], user id: [{}] processing with file {}, created in Path {}",
                    catalogProductExport.getId(), catalogProductExport.getUserId(), excelFile, fullDirectoryPath);
            String physicalPath = CatalogProductUtils.exportWriter.writeItems(excelFile, orderedColumnMappings,
                    valueMapAccordingToColumnMappings);
            return FilenameUtils.separatorsToUnix(physicalPath.substring(physicalPath.indexOf("export")));
        } catch (Exception e) {
            logger.error("Product Export Request :: exception found for export id :[{}], exception: {}",
                    catalogProductExport.getId(), e.getMessage());
            catalogProductExportCommonService.updateRecordIntoProductExportTable(catalogProductExport, null,
                    Status.FAILED.getValue());
        }
        return null;

    }


    public String writeItems(File excelFile, Map<String, String> orderedColumnMappings, List<Map<String, Object>> valueMapAsPerColumnMappings) throws Exception {

        final String[] columns = orderedColumnMappings.entrySet().stream().map(entry -> entry.getValue()).toArray(String[]::new);
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100); FileOutputStream fileOut = new FileOutputStream(excelFile);) {
            workbook.setCompressTempFiles(true);
            SXSSFSheet excelSheet = workbook.createSheet("Sheet 1");
            excelSheet.setRandomAccessWindowSize(100);
            logger.debug("Product Export Request :: Excel created with headers {}",orderedColumnMappings);
            Integer rowNumber = 0;
            Row firstRow = excelSheet.createRow(rowNumber++);
            AtomicInteger cellNumber = new AtomicInteger(0);
            // excel sheet header
            Stream.of(columns).forEach(v -> firstRow.createCell(cellNumber.getAndIncrement()).setCellValue(v));

            for (Map<String, Object> fieldsMap : valueMapAsPerColumnMappings) {
                Row row = excelSheet.createRow(rowNumber);
                cellNumber.set(0);
                for(Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
                    String val = (entry.getValue() == null) ? Constant.CHARACTERS.EMPTY : entry.getValue().toString().trim();
                    row.createCell(cellNumber.getAndIncrement()).setCellValue(val);
                }
                if (rowNumber % 4000 == 0) {
                    relaxCpu();
                }

                if (rowNumber % 10000 == 0) {
                    logger.info("Product Export Request ::  Excel processing done for rows :[{}]", rowNumber);
                }
                rowNumber++;
            }
            workbook.write(fileOut);
            workbook.dispose();
            logger.info("Product Export Request :: file created successfully with rows {}", ( rowNumber - 1 ));
            return (ObjectUtils.isNotEmpty(excelFile)) ? excelFile.getAbsolutePath() : "Success";
        } catch (IOException e) {
            logger.error("Product Export Request :: Exception encountered file name :[{}], msg -> {}", excelFile.getName(), e);
            throw e;
        }



    }

    private Pageable processPageableForSplitData(int size, int page){
        Map<String, Object> filtersMap = new HashMap<>();
        filtersMap.put("sort", Constants.DEFAULT_SORT);
        filtersMap.put("direction", "desc");
        filtersMap.put("size", size);
        filtersMap.put("page", page);

        return ControllerUtils.processPageableProperties(null, filtersMap, true);

    }

    private static void relaxCpu() {
        try {
            Thread.sleep(1);
        }catch(InterruptedException ie) {

        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
