package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;
import com.boutiqaat.catalogadminexportimportplus.repositories.CatalogProductExportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public class CatalogProductExportCommonService {

    @Autowired
    CatalogProductExportRepository catalogProductExportRepository;

    private static final Logger logger = LoggerFactory.getLogger(CatalogProductExportCommonService.class);


    @Transactional
    public CatalogProductExport updateRecordIntoProductExportTable(CatalogProductExport catalogProductExport,
                                                                   String path, String status) {
        try {
            CatalogProductExport export = catalogProductExportRepository.getOne(catalogProductExport.getId());
            export.setExportCompletedAt(new Timestamp(System.currentTimeMillis()));
            export.setExportLink(path);
            export.setStatus(status);
            logger.info("Product Export Request :: update status [{}],for export id :[{}], user :[{}] ", status,
                    catalogProductExport.getId(), catalogProductExport.getUserId());
            return catalogProductExportRepository.saveAndFlush(export);
        } catch (Exception e) {
            logger.error("Product Export Request :: excption found for export id :[{}], user id: [{}]",
                    catalogProductExport.getId(), catalogProductExport.getUserId());
            throw new RuntimeException(e);
        }
    }
}
