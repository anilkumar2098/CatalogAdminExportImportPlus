package com.boutiqaat.catalogadminexportimportplus.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    PENDING("PENDING"),PROCESSING("PROCESSING"), COMPLETE("COMPLETE"), FAILED("FAILED");

    private static final Map<String, Status> lookup = new HashMap<>();
    static{
        lookup.putAll(Arrays.stream(Status.values()).collect(Collectors.toMap(Status::getValue, status -> status)));
    }
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isEquals(final String status) {
        return lookup.containsKey(status);
    }

    public static boolean isComplete(final String status) {
        return COMPLETE.value.equals(status);
    }
}
