package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.common.ListingResponse;

import java.util.HashMap;
import java.util.List;

public class ListingResponseCreaterUtil {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ListingResponse createResponse(List list, long totalRecords, int totalPages){
        ListingResponse listingResponse = new ListingResponse<>();
        listingResponse.setData(list);
        listingResponse.setPageInfo(getPageInfo(totalRecords, totalPages));
        return listingResponse;
    }

    public static HashMap<String, Object> getPageInfo(long totalRecords, Integer totalPages) {
        HashMap<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("totalRecords", totalRecords);
        pageInfo.put("totalPages", totalPages);
        return pageInfo;
    }
}
