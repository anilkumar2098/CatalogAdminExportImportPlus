package com.boutiqaat.catalogadminexportimportplus.common;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {
    AE, BH, EU, KW, OM, QA, SA, US, EG, JO, LB,
    IQ;

    private static Map<String, Country> lookUp = null;

    static {
        lookUp = Stream.of(Country.values()).collect(Collectors.toMap(p -> p.name(), p -> p));
    }

    public static String of(String code) {
        if (code == null)
            return KW.toString();
        return lookUp.get(code.trim().toUpperCase()).name();
    }
}
