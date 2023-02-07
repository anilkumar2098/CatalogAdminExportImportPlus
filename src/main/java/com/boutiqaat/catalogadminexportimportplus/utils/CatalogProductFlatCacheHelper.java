package com.boutiqaat.catalogadminexportimportplus.utils;

import java.util.List;
import java.util.Map;

public interface CatalogProductFlatCacheHelper {

    public Map<String, String> getColumnMappingsForCsv(List<Map<String,String>> derivedColumnList);

}
