package com.boutiqaat.catalogadminexportimportplus.domain;


import org.apache.commons.lang3.StringUtils;

import com.boutiqaat.catalogadminexportimportplus.model.Filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestData {
    private String data;
    private String value;
    private String operator;

    public RequestData(String data) {
        this.data = data;
        this.splitData();
    }

    private void splitData() {
        if (this.data.contains("~")) {
            String[] arr = this.data.split("~");
            this.operator = arr[0];
            this.value = arr[1];
            return;
        }
        this.value = data;
        this.operator = "";
    }

    public boolean hasDelimeter() {
        return StringUtils.isNotEmpty(this.operator);
    }

    public boolean hasNotInOperator() {
        return operator != null
                && (operator.equals(Filter.NOT_IN)
                || operator.equals(Filter.NOT_FIND_IN_SET)
                || operator.equals(Filter.IS_NOT_NULL));
    }
}
