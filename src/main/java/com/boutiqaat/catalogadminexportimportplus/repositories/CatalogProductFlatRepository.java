package com.boutiqaat.catalogadminexportimportplus.repositories;

import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import com.boutiqaat.catalogadminexportimportplus.constants.SqlStatements;

public interface CatalogProductFlatRepository extends JpaRepository<CatalogProductFlat, Integer>, CatalogProductFlatCustomRepository<CatalogProductFlat, Integer>, JpaSpecificationExecutor<CatalogProductFlat> {

    @Async("catalogQueryExecutor")
    @Modifying
    @Query(value = SqlStatements.TRUNCATE_FLAT_PRODUCT_TABLE, nativeQuery = true)
    @Transactional
    public void truncateAllData();


    /**
     * @param productIdList
     * @return
     */
    @Modifying
    @Query(value = SqlStatements.DELETE_PRODUCTS_FROM_FLAT_PRODUCT_TABLE, nativeQuery = true)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteProducts(@Param("productIds") Set<Integer> productIds);


    /**
     * @param productIdList
     * @return
     */
    @Modifying
    @Query(value = SqlStatements.DELETE_PRODUCTS_FROM_FLAT_PRODUCT_TABLE_USING_ROW_IDS, nativeQuery = true)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteProductsUsingRowIds(@Param("productRowIds") List<Integer> productRowIds);

    @Query(value = SqlStatements.COLUMN_NAMES_FROM_FLAT_PRODUCT_TABLE, nativeQuery = true)
    public List<String> getColumnNames();

    /**
     * @return
     */
    @Async("catalogQueryExecutor")
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = SqlStatements.TRUNCATE_FLAT_PRODUCT_TABLE, nativeQuery = true)
    public CompletableFuture<Void> truncateTable();

    /**
     * @param productId
     * @param storeId
     */
    public CatalogProductFlat findByProductIdAndStoreId(Integer productId, Integer storeId);


    @Query(value = SqlStatements.FETCH_BUNDLE_CONF_PRODUCT_QUANTITY, nativeQuery = true)
    public List<BundleConfProductQuantity> fetchQuantityForBundleConfigurableProduct(List<String> skuList);


    public interface BundleConfProductQuantity{
        String getSku();
        Integer getQuantity();
    }

}
