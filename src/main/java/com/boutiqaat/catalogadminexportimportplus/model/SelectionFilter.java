package com.boutiqaat.catalogadminexportimportplus.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SelectionFilter {

    LEAF_CATEGORY_IDS("leaf_category_id^selected"), ROW_IDS("row_id^selected");

    private String value;

}
