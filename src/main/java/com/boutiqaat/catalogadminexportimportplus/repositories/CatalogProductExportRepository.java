package com.boutiqaat.catalogadminexportimportplus.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;


public interface CatalogProductExportRepository extends JpaRepository<CatalogProductExport, Integer>,
        CatalogProductExportCustomRepository<CatalogProductExport, Integer>,
        JpaSpecificationExecutor<CatalogProductExport> {

    public List<CatalogProductExport> findByUserIdAndStatus(Long userId, String status);

    Page<CatalogProductExport> findByUserId(Long userId, Pageable pages);

    @Modifying
    @Transactional
    @Query("update CatalogProductExport cp set cp.status = 'FAILED' where cp.id IN (:exportIds)")
    void updateStatusAsFailed(@Param("exportIds") Collection<Integer> exportIds);
}
