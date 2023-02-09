package com.boutiqaat.catalogadminexportimportplus.entity;


import com.boutiqaat.catalogadminexportimportplus.entity.JsonToUserConfigConverter;
import com.boutiqaat.catalogadminexportimportplus.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
@Component
public class JsonToUserConfigConverter
        implements AttributeConverter<String, UserConfiguration.UserConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToUserConfigConverter.class);

    @Override
    @SuppressWarnings("unchecked")
    public UserConfiguration.UserConfig convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return new UserConfiguration.UserConfig();
        }
        try {
            return JsonUtils.decode(attribute, UserConfiguration.UserConfig.class);
        } catch (Exception e) {
            LOGGER.error("Convert error while trying to convert string(JSON) to map data structure.");
        }
        return new UserConfiguration.UserConfig();
    }

    @Override
    public String convertToEntityAttribute(UserConfiguration.UserConfig dbData) {
        try {
            return JsonUtils.encode(dbData);
        } catch (Exception e) {
            LOGGER.error("Could not convert map to json string.");
            return null;
        }
    }
}
