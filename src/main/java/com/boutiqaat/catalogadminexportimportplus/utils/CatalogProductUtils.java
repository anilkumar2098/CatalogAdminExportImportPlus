package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.batch.ExportWriter;
import com.boutiqaat.catalogadminexportimportplus.common.Constant;
import com.google.common.base.Ascii;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogProductUtils {

    private static final Logger logger = LoggerFactory.getLogger(CatalogProductUtils.class);




    public static final String processCamelCaseString(String s) {
        final String[] words = s.split("_");
        final StringBuffer camelCaseStr = new StringBuffer();
        for (int j = 0; j < words.length; j++) {
            if (j == 0) {
                camelCaseStr.append(words[j]);
            } else {
                camelCaseStr.append(Ascii.toUpperCase(words[j].charAt(0)) + Ascii.toLowerCase(words[j].substring(1)));
            }
        }
        String retValue = camelCaseStr.toString();
        return retValue;
    }

    public static String writeExcelFile(File excelFile, Map<String, String> orderedColumnMappings, List<Map<String, Object>> valueMapAsPerColumnMappings) throws Exception {

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
    private static void relaxCpu() {
        try {
            Thread.sleep(1);
        }catch(InterruptedException ie) {

        }
    }


    public static List<String> trimElements(List<?> listValue) {
        return listValue.stream().map(str -> ((String) str).trim()).collect(Collectors.toList());
    }
}
