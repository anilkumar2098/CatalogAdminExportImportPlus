package com.boutiqaat.catalogadminexportimportplus.batch;

import com.boutiqaat.catalogadminexportimportplus.common.Constant;
import com.boutiqaat.catalogadminexportimportplus.common.ListingResponse;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.domain.ProductSearchService;
import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;
import com.boutiqaat.catalogadminexportimportplus.kafka.ExportEvent;
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
import org.springframework.batch.core.step.item.Chunk;
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

public class ExportWriter implements org.springframework.batch.item.ItemWriter<String> {

    private File file;

    private Map<String, String> orderedColumnMappings;

    private List<Map<String, Object>> valueMapAsPerColumnMappings;


    @Override
    public void write(List<? extends String> list) throws Exception {
        CatalogProductUtils catalogProductUtils = new CatalogProductUtils();

        list = Collections.singletonList(catalogProductUtils.writeExcelFile(file, orderedColumnMappings, valueMapAsPerColumnMappings));


    }
}
