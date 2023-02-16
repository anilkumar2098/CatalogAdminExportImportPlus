package com.boutiqaat.catalogadminexportimportplus.batch;

import com.boutiqaat.catalogadminexportimportplus.domain.OctopusProductSearchService;
import com.boutiqaat.catalogadminexportimportplus.domain.ProductSearchService;
import com.boutiqaat.catalogadminexportimportplus.domain.SearchProductResponseDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Service
public class ExportReader implements ItemReader<String> {

    @Autowired
    private ProductSearchService productSearchService;

    @Autowired
    private OctopusProductSearchService octopusProductSearchService;

    String request;

    Map<String, Object> filtersMap;
    Pageable pageable;
    boolean skuSearch;
    String searchType;



    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        //octopusProductSearchService.searchProducts(request);
        productSearchService.searchProducts(filtersMap, pageable, skuSearch, searchType);
        return null;
    }
}
