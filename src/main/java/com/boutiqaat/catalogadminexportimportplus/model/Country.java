package com.boutiqaat.catalogadminexportimportplus.model;


import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum Country {

    KW(Currency.KWD), AE(Currency.AED), BH(Currency.BHD), QA(Currency.QAR), SA(Currency.SAR), OM(Currency.OMR),
    IQ(Currency.USD), JO(Currency.USD);

    private Currency currency;

    /**
     *
     * @param currency
     */
    Country(Currency currency) {
        this.currency = currency;
    }

    /**
     *
     * @param country
     * @return
     */
    @JsonCreator
    public static Country getCountry(final String country) {
        return Country.valueOf(country.toUpperCase());
    }

    /**
     *
     * @param country
     * @return
     */
    public static Country getContextCountry() {
        return getCountry(LocaleContextHolder.getLocale().getCountry());
    }

    /**
     *
     * @return
     */
    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}