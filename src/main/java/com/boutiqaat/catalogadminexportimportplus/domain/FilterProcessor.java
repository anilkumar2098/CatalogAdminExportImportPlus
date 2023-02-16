package com.boutiqaat.catalogadminexportimportplus.domain;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface FilterProcessor {

    SearchProductRequestDto processFilters(Map<String, Object> filtersMap, Pageable pageable);



}
