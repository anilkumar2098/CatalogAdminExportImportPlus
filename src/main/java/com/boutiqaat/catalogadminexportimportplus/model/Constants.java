package com.boutiqaat.catalogadminexportimportplus.model;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DEFAULT_SORT = "productId";

    public static final int DEFAULT_RECORDS_PER_PAGE = 20;

    public static final int DEFAULT_PAGE_NUMBER = 0;

    public static final String EXPORT_UPLOAD_FOLDER = "/opt/magento/export";

    public static final String FILE_NAME_PREFIX = "catalog_product_";

    public static final DateTimeFormatter DATE_EXCEL_FORMATTER = DateTimeFormatter.ofPattern(Constants.CATALOG_DATE_EXCEL_FORMAT);

    public static final String CATALOG_DATE_EXCEL_FORMAT = "MMM_dd_yyyy_hh_mm_ss_a";

    public static final Integer EXPORT_PAGE_SIZE = 100;

    public static final String SEARCH_TYPE_LISTING = "listing";

    public static final String LEAF_CATEGORY_ID = "leaf_category_id";

    public static final String ROW_ID 								= "row_id";

    public static final String SELECTED = "selected";




    public class USER_CONFIGURATION {
        public static final String PRODUCT_PAGE_ID 		= "product_listing";
    }








}
