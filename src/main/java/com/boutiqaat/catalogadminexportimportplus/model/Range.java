package com.boutiqaat.catalogadminexportimportplus.model;

import lombok.Data;

@Data
public class Range {

    private String from;

    private String to;

    private boolean negate;

    /**
     *
     * @return
     */
    public String getFrom() {
        return from == null ? String.valueOf(Integer.MIN_VALUE) : from;
    }

    /**
     *
     * @return
     */
    public String getTo() {
        return to == null ? String.valueOf(Integer.MAX_VALUE) : to;
    }

}