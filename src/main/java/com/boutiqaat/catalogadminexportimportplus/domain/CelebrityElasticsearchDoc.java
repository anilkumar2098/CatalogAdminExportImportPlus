package com.boutiqaat.catalogadminexportimportplus.domain;


import com.boutiqaat.catalogadminexportimportplus.utils.MapperUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/*
@author Ramandeep Singh
*/
@Slf4j
public class CelebrityElasticsearchDoc {
    @JsonProperty("celebrity_id")
    private int celebrityId;

    @JsonProperty("banner_filter_key")
    private String bannerFilterKey;

    @JsonProperty("banner_filter_value")
    private String bannerFilterValue;

    @JsonProperty("banner_image")
    private String bannerImage;

    @JsonProperty("banner_image_arabic")
    private String bannerImageArabic;

    @JsonProperty("banner_image_arabic_v2")
    private String bannerImageArabicV2;

    @JsonProperty("banner_image_v2")
    private String bannerImageV2;

    @JsonProperty("banner_url_key")
    private String bannerUrlKey;

    @JsonProperty("banner_url_value")
    private String bannerUrlValue;

    @JsonProperty("boutique_arabic_name")
    private String boutiqueArabicName;

    @JsonProperty("boutique_name")
    private String boutiqueName;

    @JsonProperty("celebrity_arabic_description")
    private String celebrityArabicDescription;

    @JsonProperty("celebrity_arabic_name")
    private String celebrityArabicName;

    @JsonProperty("celebrity_cache_expiration_time")
    private int celebrityCacheExpirationTime;

    @JsonProperty("celebrity_code")
    private String celebrityCode;

    @JsonProperty("celebrity_description")
    private String celebrityDescription;

    @JsonProperty("celebrity_email_id")
    private String celebrityEmailId;

    @JsonProperty("celebrity_gender")
    private int celebrityGender;

    @JsonProperty("celebrity_name")
    private String celebrityName;

    @JsonProperty("celebrity_status")
    private byte celebrityStatus;

    @JsonProperty("celebrity_type")
    private int celebrityType;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("erp_account_number")
    private int erpAccountNumber;

    @JsonProperty("include_home_slider")
    private byte includeHomeSlider;

    @JsonProperty("last_ad_number")
    private int lastAdNumber;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("main_image_arabic")
    private String mainImageArabic;

    @JsonProperty("main_image_v2")
    private String mainImageV2;

    @JsonProperty("meta_description")
    private String metaDescription;

    @JsonProperty("meta_keyword")
    private String metaKeyword;

    @JsonProperty("mobile_banner_image")
    private String mobileBannerImage;

    @JsonProperty("mobile_banner_image_arabic")
    private String mobileBannerImageArabic;

    @JsonProperty("store_ids")
    private String storeIds;

    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @JsonProperty("web_banner_image")
    private String webBannerImage;

    @JsonProperty("web_banner_url")
    private String webBannerUrl;

    public Map<String, Object> convertToMap() {
        Map<String, Object> myObjectAsDict = new LinkedHashMap<>();
        try {
            myObjectAsDict = MapperUtils.convertValue(this, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            log.error("Exception ",e);
        }
        return myObjectAsDict;
    }

}
