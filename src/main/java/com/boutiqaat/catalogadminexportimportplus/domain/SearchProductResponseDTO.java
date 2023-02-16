package com.boutiqaat.catalogadminexportimportplus.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchProductResponseDTO {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("status")
    private String status;

    @JsonProperty("info")
    private Info pageInfo;

    @JsonProperty("products")
    private List<Map<String, Object>> data;

    @JsonProperty("result")
    private ErrorResponse errorResponse;

    public List<Map<String, Object>> getData() {
        if(ObjectUtils.isEmpty(data))
            data = Collections.emptyList();
        return this.data;
    }

    public boolean isEmpty() {
        return ObjectUtils.isEmpty(this.getData());
    }

    public boolean isNotEmpty() {
        return ! isEmpty();
    }
    @Data
    @NoArgsConstructor
    public static class Info {
        @JsonProperty("page_hits")
        private int recordsPerPage;

        @JsonProperty("total_hits")
        private int totalRecords;

        @JsonProperty("relation")
        private String relation;

        @JsonProperty("took")
        private int timeTakenToProcess;

        public Integer getTotalPages() {
            if(recordsPerPage == 0)
                return 0;
            if(recordsPerPage == totalRecords)
                return 1;
            int pages = totalRecords % recordsPerPage == 0
                    ? totalRecords / recordsPerPage
                    : totalRecords / recordsPerPage + 1;
            return pages;
        }
    }

    @Data
    @NoArgsConstructor
    public static class ErrorResponse {

        @JsonProperty("message")
        private String errorMessage;

        @JsonProperty("code")
        private String errorCode;
    }
}
