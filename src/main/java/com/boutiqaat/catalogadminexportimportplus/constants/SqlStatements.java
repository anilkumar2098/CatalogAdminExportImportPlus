package com.boutiqaat.catalogadminexportimportplus.constants;

public class SqlStatements {

    public static final String TRUNCATE_FLAT_PRODUCT_TABLE = "TRUNCATE TABLE catalog_product_flat";

    public static final String DELETE_PRODUCTS_FROM_FLAT_PRODUCT_TABLE = "delete from catalog_product_flat where product_id IN (:productIds)";

    public static final String DELETE_PRODUCTS_FROM_FLAT_PRODUCT_TABLE_USING_ROW_IDS = "delete from catalog_product_flat where row_id IN (:productRowIds)";

    public static final String COLUMN_NAMES_FROM_FLAT_PRODUCT_TABLE = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='boutiqaat_catalog' AND TABLE_NAME='catalog_product_flat'";

    public static final String FETCH_BUNDLE_CONF_PRODUCT_QUANTITY = "select sku, quantity_and_stock_status as quantity from boutiqaat_catalog.catalog_product_flat where sku in (:skuList) and store_id = 0";


}
