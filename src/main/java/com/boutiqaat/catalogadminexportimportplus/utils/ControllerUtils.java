package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.model.Constants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerUtils {

    public static Pageable processPageableProperties(Pageable pageable, Map<String, ? extends Object> filtersMap, boolean maxPageSize) {
        boolean paginationProvided = false;
        Object obj = null;
        if (ObjectUtils.isNotEmpty(filtersMap)) {
            int size = Constants.DEFAULT_RECORDS_PER_PAGE;
            if (maxPageSize) {
                size = Integer.MAX_VALUE;
            }
            if (filtersMap.containsKey("size")) {
                paginationProvided = true;
                obj = filtersMap.get("size");
                if (obj instanceof Integer) {
                    size = (Integer)filtersMap.get("size");
                } else {
                    size = Integer.parseInt(obj.toString());
                }
                filtersMap.remove("size");
            }
            int page = Constants.DEFAULT_PAGE_NUMBER;
            if (filtersMap.containsKey("page")) {
                paginationProvided = true;
                obj = filtersMap.get("page");
                if (obj instanceof Integer) {
                    page = (Integer)filtersMap.get("page");
                } else {
                    page = Integer.parseInt(obj.toString());
                }
                if (page > 0) {
                    page = page - 1;
                }
                filtersMap.remove("page");
            }
            String sort = Constants.DEFAULT_SORT;
            if (filtersMap.containsKey("sort")) {
                paginationProvided = true;
                sort = (String)filtersMap.get("sort");
                filtersMap.remove("sort");
            }
            Sort.Direction direction = Sort.Direction.DESC;
            if (filtersMap.containsKey("direction")) {
                paginationProvided = true;
                direction = Sort.Direction.fromString((String)filtersMap.get("direction"));
                filtersMap.remove("direction");
            }
            if (paginationProvided) {
                if (sort.contains(",")) {
                    String[] sortProperties = sort.split(",");
                    List<Sort.Order> orderList = new ArrayList<>();
                    for(int i = 0; i < sortProperties.length; i += 2) {
                        Sort.Direction dir = Sort.Direction.fromString(sortProperties[i+1]);
                        orderList.add(new Sort.Order(dir, CatalogProductUtils.processCamelCaseString(sortProperties[i])));
                    }
                    pageable = PageRequest.of(page, size, Sort.by(orderList));
                } else {
                    pageable = PageRequest.of(page, size, Sort.by(direction, sort));
                }
            }
        }
        return pageable;
    }
}
