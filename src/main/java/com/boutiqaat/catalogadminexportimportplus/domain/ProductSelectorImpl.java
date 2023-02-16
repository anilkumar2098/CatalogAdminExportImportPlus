package com.boutiqaat.catalogadminexportimportplus.domain;

import com.boutiqaat.catalogadminexportimportplus.model.SelectionFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.boutiqaat.catalogadminexportimportplus.common.Constant.CHARACTERS.COMMA;
import static com.boutiqaat.catalogadminexportimportplus.model.Constants.*;

@Component
@Slf4j
public class ProductSelectorImpl implements ProductSelector{


    @Override
    public Map<String, Set<Integer>> extractKeysNeedToMarkSelected(final Map<String, Object> filters) {
        Map<String, Set<Integer>> result = new HashMap<>();
        Arrays.stream(SelectionFilter.values()).forEach(key -> {
            Object valuesMappedOnThisKey = filters.remove(key.getValue());
            if (ObjectUtils.isNotEmpty(valuesMappedOnThisKey)) {
                Set<Integer> values = Stream.of(valuesMappedOnThisKey.toString().split(COMMA))
                        .filter(ObjectUtils::isNotEmpty).map(p -> Integer.parseInt(p.trim()))
                        .collect(Collectors.toSet());
                result.put(key.getValue(), values);
            }
        });
        return result;
    }

    @Override
    public void markProductsSeleted(List<Map<String, Object>> response, Map<String, Set<Integer>> selectionPointer) {
        log.info("make product id selected for :{}", selectionPointer);
        boolean categoryShouldSelected = ObjectUtils
                .isNotEmpty(selectionPointer.get(SelectionFilter.LEAF_CATEGORY_IDS.getValue()));
        boolean rowIdShouldSelected = ObjectUtils
                .isNotEmpty(selectionPointer.get(SelectionFilter.ROW_IDS.getValue()));
        if (categoryShouldSelected || rowIdShouldSelected) {
            for (Map<String, Object> data : response) {
                if (categoryShouldSelected && ObjectUtils.isNotEmpty(data.get(LEAF_CATEGORY_ID))) {
                    String leafCategories = data.get(LEAF_CATEGORY_ID).toString();
                    Arrays.stream(leafCategories.split(COMMA)).forEach(o -> {
                        if (selectionPointer.get(SelectionFilter.LEAF_CATEGORY_IDS.getValue())
                                .contains(Integer.parseInt(o)))
                            data.put(SELECTED, true);
                    });
                } else if (rowIdShouldSelected && selectionPointer.get(SelectionFilter.ROW_IDS.getValue())
                        .contains(Integer.parseInt(data.get(ROW_ID).toString()))) {
                    data.put(SELECTED, true);
                }
            }
        }
    }
}
