package com.boutiqaat.catalogadminexportimportplus.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductBean;


public interface CatalogProductFlatCustomRepository<T, ID> {

    public void saveAll(Map<String, CatalogProductBean> writeMap, PreparedStatement preparedStatement)  throws SQLException;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateProductFlatTableAttributes(CriteriaUpdate<CatalogProductFlat> updateCriteria);

    public CriteriaBuilder getCriteriaBuilder();

    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable, Integer count, Class<T> domainClass, Set<String> columnNames);

    public Page<T> findWithCustomOrdering(@Nullable Specification<T> spec, Pageable pageable, Class<T> domainClass, List<Integer> orderList);

    public long countWithCustomOrdering(@Nullable Specification<T> spec, Class<T> domainClass);


    /**
     * @param writeMap
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void writeToFlatTable(Map<String, CatalogProductBean> writeMap);

    /**
     * @param alterQuery
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void executeNativeQuery(String alterQuery);
}

