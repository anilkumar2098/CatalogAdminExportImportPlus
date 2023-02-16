package com.boutiqaat.catalogadminexportimportplus.model;


import java.util.Locale;
import java.util.stream.Stream;

import com.boutiqaat.catalogadminexportimportplus.common.Country;

import liquibase.pro.packaged.ar;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LocaleStore {
    DEFAULT(0), EN(1), AR(3);

    private int value;

    public static Locale of(int storeId, String country) {

        String language = null;

        switch(storeId) {
            case 3:
                language = LocaleStore.AR.name();
                break;
            default:
                language = LocaleStore.EN.name();
        }
        return new Locale.Builder().setLanguage(language).setRegion(Country.of(country)).build();
    }

    public static Integer getStoreId(String language) {
        if(language.toUpperCase().equals(EN.name()))
            return DEFAULT.value;
        return AR.value;
    }
}
