package com.boutiqaat.catalogadminexportimportplus.common;

import com.boutiqaat.catalogadminexportimportplus.exception.GenericException;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class CommonUtil {

    private static final Map<String, Integer> storeMap =
            ImmutableMap.<String, Integer> builder()
                    .put("GLOBAL_STORE", 0)
                    .put("ENGLISH_STORE", 1)
                    .put("ARABIC_STORE", 3)
                    .build();

    public static boolean isValidStore(int storeId) {
        return storeMap.containsValue(storeId);
    }

    public static boolean isAttributeScopeGlobal(String scopeValue) {
        return (Objects.nonNull(scopeValue) && Constant.ATTRIBUTE_SCOPE_GLOBAL.equalsIgnoreCase(scopeValue));
    }

    public static Date convertStringToUtilDate(String dateInString) {
        if(ObjectUtils.isEmpty(dateInString))
            return null;
        return convertStringToUtilDate(dateInString, StringUtils.EMPTY);
    }

    public static Date convertStringToUtilDate(String dateInString, String dateFormat) {
        if(ObjectUtils.isEmpty(dateInString))
            return null;
        if(ObjectUtils.isEmpty(dateFormat))
            dateFormat = Constant.PRODUCT_DATE_FORMAT;
        LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(dateFormat)) ;
        return Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());
    }

    public static String convertLdtToStr(LocalDateTime ldt) {
        if(ObjectUtils.isEmpty(ldt))
            return Constant.CHARACTERS.EMPTY;
        ZonedDateTime zonedDateTime = ldt.atZone(ZoneId.of(Constant.UTC_TIME_ZONE));
        ldt = zonedDateTime.withZoneSameInstant(ZoneId.of(Constant.KUWAIT_ZONE)).toLocalDateTime();
        return (ldt.format(DateTimeFormatter.ofPattern(Constant.DISPLAY_DT_FORMAT_FE)));
    }

    public static BigDecimal roundUptoThreePlaces(BigDecimal value){
        return roundOffBigDecimal(value, 3, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundOffBigDecimal(BigDecimal value, int scale, RoundingMode roundingMode){
        if(ObjectUtils.isNotEmpty(value)){
            BigDecimal price = value.setScale(scale, roundingMode);
            return price;
        }
        return value;
    }

    public static Long getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(ObjectUtils.isNotEmpty(principal) && (principal instanceof AdminUser)){
            return Long.parseLong((((AdminUser) principal).getId())+"");
        }
        throw new GenericException("Problem in fetching user ID of logged in user");
    }

    /**
     * This method will check if logged in user has this permission
     * @param permission - permission name to check if user have
     * @return boolean - if user has permission return true else return false
     */
    public static boolean havePermission(String permission) {
        return ((CustomAdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getPermissions().contains(permission);
    }

    /**
     * This method will check if logged in user dosen't have this permission
     * @param permission - permission name to check if user have
     * @return boolean - if user doesn't have this permission return true else return false
     */
    public static boolean dontHavePermission(String permission) {
        return ! havePermission(permission);
    }

    public static Timestamp getUTCCurrentTime() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime ldt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneOffset.UTC).toLocalDateTime();
        return Timestamp.valueOf(ldt);
    }
}
