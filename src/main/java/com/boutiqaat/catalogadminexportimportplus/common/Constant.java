package com.boutiqaat.catalogadminexportimportplus.common;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public final class Constant {	private Constant() {}

    public static final String KUWAIT_ZONE = "Asia/Kuwait";
    public static final String FILTER_DATE_FORMAT = "YYYY-MM-dd";
    public static final DateTimeFormatter SQL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FILTER_DATE_FORMAT);
    public static final String PRODUCT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ATTRIBUTE_SCOPE_GLOBAL = "GLOBAL";
    public static final String DISPLAY_DT_FORMAT_FE = "MMM dd, yyyy hh:mm:ss a";
    public static final String UTC_TIME_ZONE = "UTC";
    public static final String UTF8 = "UTF-8";

    public static final class FileExtensions { private FileExtensions() {}
        public static final String CSV = ".csv";
    }

    public static final class MESSAGES {	private MESSAGES() {}
        public static final String GENERIC_ERR_CODE = "500";
        public static final String INVALID_REQ_PARAM_ERR_CODE = "422";
        public static final String INVALID_FILE_EXT_ERR_MSG = "File not supported.";
        public static final String GENERIC_ERR_MSG = "Something went wrong, please try again";
        public static final String INVALID_STORE = "Store id must be either 0, or 1 or 3";
        public static final String GENERIC_NOT_FOUND_ERROR_MESSAGE = "NOT FOUND ";
        public static final String GENERIC_NOT_FOUND_ERROR_CODE = "404";
        public static final String GENERIC_NOT_NULL_ERROR_MESSAGE = "must be not null";
        public static final String GENERIC_NOT_Blank_ERROR_MESSAGE = "must be not Blank";
        public static final String ERROR_LOG_TWO_MESSAGES = "Error message :{}, {}";
    }

    public static final class CHARACTERS {	private CHARACTERS() {}

        public static final String EMPTY = "";

    }

    public static final class NUMBERS {	private NUMBERS() {}
        public static final int ZERO = 0;
    }

    public static class EXPORT_FILE {
        public static final String EXPORT_FILE_SUFFIX_DT_PATTERN = "yyyy_MM_dd_HH_mm_ss";
        public static final DateTimeFormatter EXPORT_FILE_DT_FORMATTER = DateTimeFormatter.ofPattern(EXPORT_FILE_SUFFIX_DT_PATTERN);
    }

    public static class ATTRIBUTES {
        public static final String ATTRIBUTE_DATA_KEY = "attributes_data";
        public static final String SELECT = "select";
        public static final String MULTI_SELECT = "multiselect";
        public static final String PAGE_BUILDER = "pagebuilder";
        public static final String TEXT_AREA = "textarea";
        public static final String MULTI_LINE = "multiline";
        public static final String TEXT = "text";
        public static final String TEXTAREA = "textarea";
        public static final String PAGEBUILDER = "pagebuilder";
        public static final String TEXTEDITOR = "texteditor";
        public static final String PRICE = "price";
        public static final String DATE = "date";
        public static final String MULTISELECT = "multiselect";
        public static final String WEEE = "weee";
        public static final String SWATCH_VISUAL = "swatch_visual";
        public static final String SWATCH_TEXT = "swatch_text";
        public static final String MEDIA_IMAGE = "media_image";
        public static final String BOOLEAN = "boolean";
        public static final String WEIGHT = "weight";
        public static final String GALLERY = "gallery";
        public static final List<String> SWATCH_SET = Arrays.asList(SWATCH_VISUAL, SWATCH_TEXT);
    }

    public static final class Regex {
        public static final String UPPER_CASE = "[A-Z]+";

        private Regex() {}
    }

}

