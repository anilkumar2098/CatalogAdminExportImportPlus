package com.boutiqaat.catalogadminexportimportplus.domain;

import com.boutiqaat.catalogadminexportimportplus.common.Gender;
import com.boutiqaat.catalogadminexportimportplus.entity.UserConfiguration;
import com.boutiqaat.catalogadminexportimportplus.model.*;
import com.boutiqaat.catalogadminexportimportplus.utils.CatalogProductUtils;
import com.boutiqaat.catalogadminexportimportplus.utils.UserConfigurationUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;


@Service
public class FilterProcessorImpl implements FilterProcessor {

    @Value("#{new Boolean('${catalog.products.createdin_updatedin_check}')}")
    private boolean productValidityCheck;

    private static Map<String, String> filterKeyRepalcementMap = null;

    @Value("${catalog.product.images.url.prefix}")
    private String imageUrlPrefix;

    @Override
    public SearchProductRequestDto processFilters(Map<String, Object> filtersMap, Pageable pageable) {
        SearchProductRequestDto searchRequest = SearchProductRequestDto.builder().page(pageable.getPageNumber())
                .size(pageable.getPageSize()).currencyDisplay("ORIGINAL").locale(LocaleContextHolder.getLocale()).build();
        searchRequest.setSort(pageable.getSort());
        if (ObjectUtils.isNotEmpty(filtersMap)) {
            for (Map.Entry<String, ? extends Object> entry : filtersMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (ObjectUtils.isEmpty(key) || ObjectUtils.isEmpty(value)) {
                    log.info("Key :[{}], value :[{}]");
                    continue;
                }
                // If key got from front end is different from the key in octopus then change
                if(filterKeyRepalcementMap.get(key) != null)
                    key = filterKeyRepalcementMap.get(key);
                switch (key) {
                    case "include_source":
                        searchRequest.setIncludeSource(trimStringsInCollection((List<?>) value));
                        break;
                    case "productIdFrom":
                        setFromFilter("product_ids", searchRequest, value);
                        break;
                    case "productIdTo":
                        setToFilter("product_ids", searchRequest, value);
                        break;
                    case "priceFrom":
                    case "ksa_price_from":
                        setFromFilter("price", searchRequest, value);
                        break;
                    case "priceTo":
                    case "ksa_price_to":
                        setToFilter("price", searchRequest, value);
                        break;
                    case "specialPriceFrom":
                    case "ksa_special_price_from":
                        setFromFilter("selling_price", searchRequest, value);
                        break;
                    case "specialPriceTo":
                    case "ksa_special_price_to":
                        setToFilter("selling_price", searchRequest, value);
                        break;
                    case "ksa_price":
                        setFromFilter("price", searchRequest, value);
                        setToFilter("price", searchRequest, value);
                        break;
                    case "ksa_special_price":
                        setFromFilter("selling_price", searchRequest, value);
                        setToFilter("selling_price", searchRequest, value);
                        break;
                    case "quantityFrom":
                        setFromFilter("stocks", searchRequest, value);
                        break;
                    case "quantityTo":
                        setToFilter("stocks", searchRequest, value);
                        break;
                    case "ksa_quantity":
                        setFromFilter("stocks", searchRequest, value);
                        setToFilter("stocks", searchRequest, value);
                        break;
                    case "readyDateFrom":
                        // TODO - need to do because Ehab is working on it "ready_date"
                        setFromFilter("ready_date", searchRequest, changeDateFormatForOctopus(value.toString()));
                        break;
                    case "readyDateTo":
                        // TODO - need to do because Ehab is working on it "ready_date"
                        setToFilter("ready_date", searchRequest, changeDateFormatForOctopus(value.toString()));
                        break;
                    case "device_id":
                        searchRequest.setDeviceId(value.toString());
                        break;
                    case "visibility":
                        searchRequest.setVisibility(Visibility.of(Integer.parseInt(value.toString().trim())));
                        break;
                    case "type_id":
                        searchRequest.setProductTypes(
                                Stream.of(value.toString().replace(" ", "").split(",")).collect(Collectors.toSet()));
                        break;
                    case "types":
                        // same as above but this is for more than one product type
                        searchRequest.setProductTypes(trimStringsInCollection((List<?>) value));
                        break;
                    case "manufacturer" :
                        searchRequest.setManufacturers(
                                Stream.of(value.toString().replace(" ", "").split(",")).collect(Collectors.toSet()));
                        break;
                    case "category_names":
                    case "brand_name":
                    case "brand_arabic_name":
                    case "keyword":
                        // contains search in this section
                        String query = searchRequest.getQuery();
                        query = StringUtils.isBlank(query) ? value.toString() : query + " " + value.toString();
                        searchRequest.setQuery(query);
                        break;
                    case "category_ids":
                    case "leaf_category_id":
                        RequestData categories = new RequestData(value.toString());
                        searchRequest
                                .setCategories(categories.hasNotInOperator() ? convertToSetAndGet(value, categories, true)
                                        : convertToSetAndGet(value, categories, false));

                        break;
                    case "gender":
                        String gender;
                        RequestData genders = new RequestData(value.toString());
                        if (genders.hasDelimeter()) {
                            gender = Gender.of(Arrays.asList(genders.getValue().toString().replace(" ", "").split(",")));
                            if(genders.hasNotInOperator()) {
                                gender = "~" + gender;
                            }
                        } else {
                            if (value instanceof List<?>) {
                                gender = Gender.of(trimStringsInCollection(((List<?>) value)));
                            } else if (value.toString().contains(",")) {
                                gender = Gender.of(Arrays.asList(value.toString().replace(" ", "").split(",")));
                            } else {
                                gender = Gender.of(Arrays.asList(genders.getData()));
                            }
                        }
                        searchRequest.setGender(gender);
                        break;
                    case "sku":
                        if(!ObjectUtils.isEmpty(value.toString()) && !value.toString().trim().equalsIgnoreCase("null")) {
                            RequestData skuRequest = new RequestData(value.toString());
                            List<String> skus = null;
                            if (value instanceof List<?>) {
                                skus = trimStringsInCollection(((List<?>) value));
                            } else if (skuRequest.hasDelimeter()) {
                                skus = skuRequest.hasNotInOperator()
                                        ? Stream.of(skuRequest.getValue().replace(" ", "").split(",")).map(s -> "~" + s)
                                        .collect(Collectors.toList())
                                        : Arrays.asList(skuRequest.getValue().replace(" ", "").split(","));
                            } else {
                                skus = Arrays.asList(skuRequest.getData().replace(" ", "").split(","));
                            }
                            // we need following ordered collection later while marking products selected
                            if(!CollectionUtils.isEmpty(skus)){
                                searchRequest.setProductSkus(
                                        skus.stream().map(s -> s.toString()).collect(Collectors.toCollection(LinkedHashSet::new)));
                            }
                        }
                        break;
                    case "productId":
                        List<String> products = null;
                        if (value instanceof List<?>) { // getting product ids as integer type collection
                            searchRequest.setProductIds(((List<?>) value).stream().map(v -> ((Integer) v).toString())
                                    .collect(Collectors.toSet()));
                            break;
                        }


                        products = Arrays.asList((value.toString()).replace(" ", "").split(","));
                        searchRequest.setProductIds(products.stream().collect(Collectors.toSet()));
                        break;
                    case "productIds":
                        setFromProdIdsbyCsv("product_ids", searchRequest, value);
                        break;


                    default:
                        Map<String, Object> attributes = searchRequest.getAttributes();
                        if(attributes == null) {
                            attributes = new HashMap<>();
                            searchRequest.setAttributes(attributes);
                        }
                        RequestData attributeRequest = new RequestData(value.toString());

                        if (attributeRequest.hasDelimeter()) {

                            List<String> listValue = null;
                            String attrValue = null;
                            if (attributeRequest.hasNotInOperator()) {
                                attrValue = Arrays.stream(attributeRequest.getValue().split(",")).filter(Objects::nonNull).map(o -> "~"+o)
                                        .collect(Collectors.joining(","));
                            } else if (value instanceof List<?>)
                                listValue = trimStringsInCollection((List<?>) value);
                            else
                                attrValue = attributeRequest.getValue();
                            if(listValue != null)
                                attrValue = listValue.stream().collect(Collectors.joining(","));
                            attributes.put(key, attrValue);
                        } else {
                            List<String> listValue = null;
                            if (value instanceof List<?>) {
                                listValue  = trimStringsInCollection((List<?>) value);
                                // filter like IN clause in SQL
                                log.info("Need :[{}] operator support in octopus for this attribute :[{}]", attributeRequest.getOperator(), key);
                            } else if (value instanceof String && value.toString().contains(",")) {
                                listValue = Arrays.asList(value.toString().split(","));
                                listValue = CatalogProductUtils.trimElements(listValue);
                                // filter like IN cluase in SQL
                                log.info("Need :[{}] operator support in octopus for this attribute :[{}]", attributeRequest.getOperator(), key);
                            } else {
                                String attributeValue =  attributeRequest.getValue();
                                if(key.equals("image") && attributeValue != null) {
                                    String[] splitArray = attributeValue.split(imageUrlPrefix);
                                    if(splitArray.length > 1) {
                                        attributeValue = splitArray[1];
                                    }
                                }
                                attributes.put(key, attributeValue);
                            }
                        }
                }
            }
            if (productValidityCheck) {
                log.info("Need productValidityCheck filter support in octopus : [{}]", productValidityCheck);
                Filter dateCheck = new Filter(Filter.AND, new ArrayList<>(5));
                long currentTimeInSeconds = new Date().getTime() / 1000L;
                //dateCheck.getFilters().add(new Filter("createdIn", Filter.LESS_THAN_OR_EQUAL, currentTimeInSeconds));
                //dateCheck.getFilters().add(new Filter("updatedIn", Filter.GREATER_THAN_OR_EQUAL, currentTimeInSeconds));
                //filter.getFilters().add(dateCheck);
            }
        }
        return searchRequest;
    }





    private List<String> trimStringsInCollection(List<?> listValue) {
        return listValue.stream().map(str -> ((String) str).trim()).collect(Collectors.toList());
    }

    private void setFromFilter(String key, SearchProductRequestDto searchRequest, Object value) {
        Map<String, Range> ranges = searchRequest.getRanges();
        if(ranges == null)
            ranges = new HashMap<>();
        Range range = ranges.get(key);
        if(range == null)
            range = new Range();
        range.setFrom(getValueWithoudNegate(value, range));
        ranges.put(key, range);
        searchRequest.setRanges(ranges);
    }

    private String getValueWithoudNegate(Object val, Range range) {
        String value = val.toString();
        range.setNegate(value.startsWith("~"));
        if(range.isNegate())
            value = value.substring(1);
        return value;
    }

    private void setToFilter(String key, SearchProductRequestDto searchRequest, Object value) {
        Map<String, Range> ranges = searchRequest.getRanges();
        if(ranges == null)
            ranges = new HashMap<>();
        Range range = ranges.get(key);
        if(range == null)
            range = new Range();
        range.setTo(getValueWithoudNegate(value, range));
        ranges.put(key, range);
        searchRequest.setRanges(ranges);
    }

    private String changeDateFormatForOctopus(String dateString) {
        LocalDateTime datetime = null;
        try {
            if(dateString.contains("Arabian"))
                datetime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z '(Arabian Standard Time)'"));
            else
                datetime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z '(Eastern European Standard Time)'"));
        } catch(Exception e) {
            datetime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z '('z')'"));
        }
        return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    private Set<String> convertToSetAndGet(Object value, RequestData requestData, Boolean negate) {
        Set<String> valueSet;
        if (value instanceof List<?>) {
            List<?> listValue = (List<?>) value;
            valueSet = listValue.stream().filter(Objects::nonNull)
                    .map(c -> (negate ? "~" : "") + c.toString().trim()).collect(Collectors.toSet());
        } else {
            valueSet = Stream.of(requestData.getValue().replace(" ", "").split(",")).filter(Objects::nonNull)
                    .map(c -> (negate ? "~" : "") + c.toString().trim())
                    .collect(Collectors.toSet());
        }
        return valueSet;
    }

    private void setFromProdIdsbyCsv(String key, SearchProductRequestDto searchRequest, Object value) {

        searchRequest.setProductIds(Arrays.asList((value.toString()).replace(" ", "").split(",")).stream().collect(Collectors.toSet()));


    }

}
