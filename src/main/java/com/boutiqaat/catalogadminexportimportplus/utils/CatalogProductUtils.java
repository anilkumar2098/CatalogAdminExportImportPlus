package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.batch.ExportWriter;
import com.google.common.base.Ascii;

public class CatalogProductUtils {

    public static ExportWriter exportWriter;

    public static final String processCamelCaseString(String s) {
        final String[] words = s.split("_");
        final StringBuffer camelCaseStr = new StringBuffer();
        for (int j = 0; j < words.length; j++) {
            if (j == 0) {
                camelCaseStr.append(words[j]);
            } else {
                camelCaseStr.append(Ascii.toUpperCase(words[j].charAt(0)) + Ascii.toLowerCase(words[j].substring(1)));
            }
        }
        String retValue = camelCaseStr.toString();
        return retValue;
    }
}
