package com.boutiqaat.catalogadminexportimportplus.common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.boutiqaat.catalogadminexportimportplus.entity.UserConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ListingResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Object> pageInfo = new HashMap<>();
    private Status status;

    Map metadata = new LinkedHashMap<>();
    List<T> data;
    private UserConfiguration.UserConfig defaultUserConfig;

    private List<UserConfiguration> userConfigList = new ArrayList<>();

    public ListingResponse() {
        this.status = new Status();
    }
}

