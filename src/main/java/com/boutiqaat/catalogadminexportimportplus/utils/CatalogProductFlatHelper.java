package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.domain.CatalogProductFlat;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CatalogProductFlatHelper {

    public Page<Map<String, Object>> transformResultForOptionValues(Page<CatalogProductFlat> retValue);

}
