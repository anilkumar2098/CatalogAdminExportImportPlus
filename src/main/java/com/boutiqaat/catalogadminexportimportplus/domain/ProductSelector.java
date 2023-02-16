package com.boutiqaat.catalogadminexportimportplus.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductSelector {

    Map<String, Set<Integer>> extractKeysNeedToMarkSelected(final Map<String, Object> filters);

    void markProductsSeleted(List<Map<String, Object>> response, Map<String, Set<Integer>> selectionPointer);



}
