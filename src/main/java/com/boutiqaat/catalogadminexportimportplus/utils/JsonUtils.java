package com.boutiqaat.catalogadminexportimportplus.utils;


import com.boutiqaat.catalogadminexportimportplus.domain.CelebrityElasticsearchDoc;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author RamandeepSinghSoni
 */
@Slf4j
@Data
public class JsonUtils {
    private ObjectMapper mapper;
    private static JsonUtils INSTANCE = new JsonUtils();

    /**
     *
     */
    private JsonUtils() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static List<Map<String, Object>> convertCelebrityDocsToRecords(List<CelebrityElasticsearchDoc> catalogProductFlatList) {
        List<Map<String, Object>> records = new ArrayList<>();
        for (CelebrityElasticsearchDoc catalogProductFlat : catalogProductFlatList) {
            records.add(catalogProductFlat.convertToMap());
        }
        return records;
    }

    public static Map<String, Object> convertJsonFileToMap(String filePath) throws Exception {
        Map<String, Object> map;
        map = INSTANCE.mapper.readValue(new File(
                filePath), new TypeReference<Map<String, Object>>() {
        });
        return map;
    }

    /**
     * @param object the object that needs to be encoded to string
     * @return string
     */
    public static String encode(final Object object) throws Exception {
        return INSTANCE.mapper.writeValueAsString(object);
    }

    /**
     * @param object the object that needs to be encoded to string
     * @return string
     */
    public static String encodePretty(final Object object) throws Exception {
        INSTANCE.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = INSTANCE.mapper.writeValueAsString(object);
        INSTANCE.mapper.disable(SerializationFeature.INDENT_OUTPUT);
        return json;
    }


    /**
     * @param jsonString json object as string
     * @return map of String and Object similar to @jsonString
     */
    public static Map<String, Object> decode(final String jsonString) throws Exception {
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {
        };
        return INSTANCE.mapper.readValue(jsonString, typeReference);
    }

    public static <T> T decode(JsonParser jsonParser, TypeReference<T> typeReference) throws Exception {
        return INSTANCE.mapper.readValue(jsonParser, typeReference);
    }
    /**
     * @param jsonString json object as string
     * @param castAs     class type the JSON is going to be casted to
     * @return object of type @classAs
     */
    public static <T> T decode(final String jsonString, final Class<T> castAs) throws Exception {
        return INSTANCE.mapper.readValue(jsonString, castAs);
    }

    /**
     * @param jsonString json object as string
     * @return map of String and Object similar to @jsonString
     */
    public static <T> T decode(final String jsonString, TypeReference<T> typeReference) throws Exception {
        return INSTANCE.mapper.readValue(jsonString, new TypeReference<T>() {
        });
    }

    public static <T> T decode(InputStream src, Class<T> valueType) throws Exception {
        return INSTANCE.mapper.readValue(src, valueType);
    }

    public static void writeValue(OutputStream out, Object value) throws Exception {
        INSTANCE.mapper.writeValue(out, value);
    }

    public static String encode(ObjectMapper mapper, final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final IOException | IllegalArgumentException e) {
            log.error("Unable to encode object", e);
        }
        return null;
    }

    /**
     * @param object
     * @return
     */
    public static String encodePretty(ObjectMapper mapper, final Object object) {
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(object);
        } catch (final IOException | IllegalArgumentException e) {
            log.error("Unable to encode object", e);
        } finally {
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
        }
        return "";
    }

    /**
     * @param jsonString
     * @return
     */
    public static Map<String, Object> decode(ObjectMapper mapper, final String jsonString) {
        try {
            final TypeReference<HashMap<String, Object>> typeReference =
                    new TypeReference<HashMap<String, Object>>() {};
            return mapper.readValue(jsonString, typeReference);
        } catch (final IOException | IllegalArgumentException e) {
            log.error("Unable to decode object", e);
        }
        return Collections.emptyMap();
    }

    /**
     *
     * @param jsonString
     * @param castAs
     * @return
     */

    /**
     * @param jsonString
     * @param castAs
     * @return
     */
    public static <T> T decode(ObjectMapper mapper, final String jsonString,
                               final Class<T> castAs) {
        try {
            return mapper.readValue(jsonString, castAs);
        } catch (final Exception e) {
            log.error("Unable to decode object", e);
        }
        return null;
    }
}

