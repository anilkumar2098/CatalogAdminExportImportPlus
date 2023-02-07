package com.boutiqaat.catalogadminexportimportplus.kafka;

import com.boutiqaat.catalogadminexportimportplus.entity.CatalogProductExport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ExportEvent {

    @JsonProperty("exportInfo")
    Object exportInfo;

    @JsonProperty("filters")
    private Map<String, Object> filters;

    //@JsonProperty("userId")
    Long userId;

    @JsonProperty("pageId")
    private String pageId;

    private String status;

    private String exportType;

    private CatalogProductExport productExport;

}
