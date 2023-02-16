package com.boutiqaat.catalogadminexportimportplus.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Service
@FeignClient(name = "octopusProductSearchService", url = "${octopus.product.search.url}")
public interface OctopusProductSearchService {

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    SearchProductResponseDTO searchProducts(@RequestBody String request);

}