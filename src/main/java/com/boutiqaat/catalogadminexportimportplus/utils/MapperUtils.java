package com.boutiqaat.catalogadminexportimportplus.utils;


import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.Data;

/**
 * Created by Farouk Taher Date: 14-06-2020 Time: 5:47 PM
 */
@Data
public class MapperUtils {
    private ObjectMapper mapper;
    private static MapperUtils INSTANCE = new MapperUtils();

    /**
     *
     */
    private MapperUtils() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * @param source object to be parsed
     * @param clazz  target class
     * @return object of the passed class type
     */
    public static <D> D map(final Object source, final Class<D> clazz) throws Exception {
        if (source == null || clazz == null) {
            return null;
        }
        final D target = clazz.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * @param source object to be parsed
     * @param target target class
     * @return object of the passed class type
     */
    public static <D> D map(final Object source, final D target) {
        if (source == null || target == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static Map<String, Object> map(final Object source) throws Exception {
        if (source == null) {
            return null;
        }
        return PropertyUtils.describe(source);
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return INSTANCE.mapper.convertValue(fromValue, toValueTypeRef);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return INSTANCE.mapper.convertValue(fromValue, toValueType);
    }

}
