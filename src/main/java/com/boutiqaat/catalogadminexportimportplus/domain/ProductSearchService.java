package com.boutiqaat.catalogadminexportimportplus.domain;

import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface ProductSearchService {

    Object searchProducts(Map<String, Object> filtersMap, Pageable pageable, boolean skuSearch, String searchType);

}
