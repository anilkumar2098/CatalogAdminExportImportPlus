package com.boutiqaat.catalogadminexportimportplus.domain;

import com.boutiqaat.catalogadminexportimportplus.common.Gender;
import com.boutiqaat.catalogadminexportimportplus.common.ListingResponse;
import com.boutiqaat.catalogadminexportimportplus.common.Status;
import com.boutiqaat.catalogadminexportimportplus.exception.GenericException;
import com.boutiqaat.catalogadminexportimportplus.model.Constants;
import com.boutiqaat.catalogadminexportimportplus.model.LocaleStore;
import com.boutiqaat.catalogadminexportimportplus.utils.JsonUtils;
import com.boutiqaat.catalogadminexportimportplus.utils.ListingResponseCreaterUtil;
import com.boutiqaat.catalogadminexportimportplus.utils.UserConfigurationUtil.UserConfigResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.boutiqaat.catalogadminexportimportplus.model.CatalogProductConstant.MEN;
import static com.boutiqaat.catalogadminexportimportplus.model.CatalogProductConstant.WOMEN;
import static com.boutiqaat.catalogadminexportimportplus.model.CatalogProductConstant.SEARCH_TYPE_LISTING;
import static org.apache.kafka.common.requests.DeleteAclsResponse.log;


@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private FilterProcessor filterProcessor;



    @Autowired
    private ProductSelector productSelector;

    @Autowired
    private OctopusProductSearchService octopusProductSearchService;

    private final SimpleDateFormat oldFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm:ss aaa");

    private final SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private Map<Integer, String[]> categoryLevelMap = null;

    private List<String> imageAttrCodes = null;

    @Value("${catalog.product.images.url.prefix}")
    private String imageUrlPrefix;

    private List<String> yesOrNoAttributes = null;

    private Map<String, String> sortPropertyReplacementMap = null;

    private static List<String> ksaAttributes = null;

    @Override
    public Object searchProducts(Map<String, Object> filters, Pageable pageable, boolean skuSearch, String searchType) {
        log.info("Request received for product listing");

        // Fetch filter and user configuration from database
        //filters = filterProcessor.fetchSavedFilters(filters);

        // replace keys required by octopus and handle paging, sorting and locale
        pageable = handlePagingAndSorting(pageable, filters);
        UserConfigResponse userConfigResponse = (UserConfigResponse) filters.remove("userConfig");

        // extract information from filters if need to set in response as selected
        Map<String, Set<Integer>> keysToBeMarkedAsSelected = productSelector.extractKeysNeedToMarkSelected(filters);

        // create filters required by octopus
        SearchProductRequestDto request = filterProcessor.processFilters(filters, pageable);
        request.setSort(pageable.getSort());

        // Boolean attributes need to have as we need to set Yes or No, because octopus
        // sends 0 or 1
        //List<EavAttribute> booleanAttr = attributeService.getByEavEntityTypeAndFrontEndInput(4, "boolean");

        // Hit octopus to get data from
        SearchProductResponseDTO searchResult = searchProductsInOctopus(request);

        switch (searchType){
            case SEARCH_TYPE_LISTING:{
                return  getProductsListing(searchResult, userConfigResponse,
                         skuSearch, request, keysToBeMarkedAsSelected);
            }

            default:{
                throw new RuntimeException("search type ia is not specified");
            }
        }

    }

    private ListingResponse<?> getProductsListing(SearchProductResponseDTO searchResult
            , UserConfigResponse userConfigResponse, boolean skuSearch
            , SearchProductRequestDto request, Map<String, Set<Integer>> keysToBeMarkedAsSelected) {
        // map octopus response to cams required response
        ListingResponse<?> response = mapToListingResponse(searchResult, userConfigResponse);

        // set not found skus in meta data, we don't really have those in octopus
        // response
        if (skuSearch && ObjectUtils.isNotEmpty(request.getProductSkus())) {
            if (ObjectUtils.isNotEmpty(response) && ObjectUtils.isNotEmpty(response.getData()))
                identifyNotFoundSkus(request.getProductSkus(), searchResult);
            response.getMetadata().put("notFoundSkus", StringUtils.join(request.getProductSkus(), ","));
        }

        // mark products selected if required by front end
        if (ObjectUtils.isNotEmpty(keysToBeMarkedAsSelected))
            productSelector.markProductsSeleted(searchResult.getData(), keysToBeMarkedAsSelected);
        log.info("Request finished for product listing");
        return response;
    }

    private void identifyNotFoundSkus(Set<String> requestedSkus, SearchProductResponseDTO octopusResponse) {
        if (ObjectUtils.isNotEmpty(requestedSkus)) {
            octopusResponse.getData().stream().forEach(row -> requestedSkus.remove((String) row.get("sku")));
        }
    }

    private ListingResponse<?> mapToListingResponse(SearchProductResponseDTO searchResult,
                                                    UserConfigResponse userConfig) {
        int storeId = LocaleStore.getStoreId(LocaleContextHolder.getLocale().getLanguage());
        for (Map<String, Object> product : searchResult.getData()) {
            Object id = product.remove("id");
            product.put("row_id", id);
            product.put("product_id", id);
            try {
                String prdductType = (String) product.remove("type");
                prdductType = prdductType == null ? "" : prdductType;
                product.put("type_id", prdductType == null ? "" : prdductType.toLowerCase());
                product.put("visibility", product.get("visibility") == null ? "" : product.get("visibility"));
                // brand information
                Map<String, Object> attribute = (Map<String, Object>) product.remove("manufacturer");
                if (attribute != null) {
                    Object o = attribute.get("name");
                    product.put("manufacturer", o == null ? "" : o);
                    product.put("brand_name", o == null ? "" : o);
                    product.put("brand_id", attribute.get("id"));
                }
                attribute = (Map<String, Object>) product.get("exclusive");
                if (attribute != null) {
                    Object o = attribute.get("exclusive");
                    product.put("is_exclusive_to_celebrity", o == null ? "No" : o);
                }

                // preparing linear structure of attributes
                attribute = (Map<String, Object>) product.remove("attributes");
                if (attribute != null)
                    product.putAll(attribute);
                Object status = product.get("status");
                product.put("status", status == null || !status.toString().equals("1") ? "Disabled" : "Enabled");
                Object readyDate = product.get("ready_date");
                if(!ObjectUtils.isEmpty(readyDate)) {
                    product.put("ready_date", oldFormat.format(newFormat.parse(readyDate.toString())));
                }
                Map<String, Object> oo = (Map<String, Object>) product.remove("attribute_set");
                if (oo != null){
                    product.put("attribute_set_name", oo.get("value"));
                    product.put("attribute_set_id", oo.get("id"));
                }
                List<Object> list = (List<Object>) product.remove("gender");
                if (ObjectUtils.isNotEmpty(list)) {
                    String s = list.stream().map(o -> {
                        if(Gender.of(o.toString()).toString().equalsIgnoreCase(MEN)) {
                            return MEN;
                        }else {
                            return WOMEN;
                        }
                    }).collect(Collectors.joining(","));
                    product.put("gender", s);
                }

                attribute = (Map<String, Object>) product.remove("stocks");
                // to make sure following keys are available in response
                product.put("price", "");
                product.put("special_price", "");
                product.put("ksa_price", "");
                product.put("ksa_special_price", "");
                product.put("total_quantity", 0);
                // price, special price and quantity
                int totalQuantity = 0;
                if (attribute != null) {
                    Map<String, Object> info = (Map<String, Object>) attribute.get("inventory");
                    if (info != null) {

                        Map<String, Object> info1;
                        info1 = (Map<String, Object>) info.get("kw"); // specific country
                        if (info1 != null) {
                            for (String key : info1.keySet()) {
                                Map<String, Object> kwInventoryInfo = (Map<String, Object>) info1.get(key); // warehouse id
                                product.put("quantity_and_stock_status", kwInventoryInfo.get("available"));
                                totalQuantity += ((Integer) kwInventoryInfo.get("available")).intValue();
                                break;
                            }
							/*info1 = (Map<String, Object>) info1.get("1"); // warehouse id
							if (info1 != null) {
								product.put("quantity_and_stock_status", info1.get("available"));
								totalQuantity += ((Integer) info1.get("available")).intValue();
							}*/
                        }
                        // NEW: Tasks DN-9308 (Add KSA Quantity + Total Quantity (KSA + SA)):
                        // ==============================================================
                        Map<String, Object> info2;
                        info2 = (Map<String, Object>) info.get("sa"); // Get KSA Inventory Info
                        if (info2 != null) {
                            for (String key : info2.keySet()) {
                                Map<String, Object> ksaInventoryInfo = (Map<String, Object>) info2.get(key); // warehouse id
                                product.put("quantity_ksa", ksaInventoryInfo.get("available"));
                                totalQuantity += ((Integer) ksaInventoryInfo.get("available")).intValue();
                                break;
                            }
							/*info2 = (Map<String, Object>) info2.get("2"); // warehouse id
							if (info2 != null) {
								product.put("quantity_ksa", info2.get("available"));
								totalQuantity += ((Integer) info2.get("available")).intValue();
							}*/
                        }
                        product.put("total_quantity", totalQuantity);
                    }
                    info = (Map<String, Object>) attribute.get("prices");
                    if (info != null) {
                        Map<String, Object> infoInner = (Map<String, Object>) info.get("kw");
                        product.put("price", infoInner == null || infoInner.get("price") == null ? ""
                                : "KWD " + infoInner.get("price"));
                        // I was getting discount node first, but Abdalla said to get it from selling
                        // price
                        product.put("special_price", infoInner == null || infoInner.get("selling_price") == null ? ""
                                : "KWD " + infoInner.get("selling_price"));

                        // ksa price and special price
                        infoInner = (Map<String, Object>) info.get("sa");
                        product.put("ksa_price", infoInner == null || infoInner.get("price") == null ? ""
                                : "SAR " + infoInner.get("price"));
                        // DN-10846:: I was getting discount node first, but Abdalla said to get it from
                        // selling price
                        product.put("ksa_special_price",
                                infoInner == null || infoInner.get("selling_price") == null ? ""
                                        : "SAR " + infoInner.get("selling_price"));

                    }
                }
                // country exclude
                attribute = (Map<String, Object>) product.remove("country_exclude");
                if (attribute != null) {
                    String countryExclude = attribute.entrySet().stream()
                            .filter(o -> Boolean.parseBoolean(o.getValue().toString())).map(o -> o.getKey())
                            .collect(Collectors.joining(","));
                    product.put("country_exclude", countryExclude);
                }
                list = (List<Object>) product.remove("categories");
                // creating categories -> like level one category, leaf categories etc
                if (list != null && !list.isEmpty()) {
                    StringBuilder catLabels = new StringBuilder("");
                    StringBuilder catIds = new StringBuilder("");
                    Set<Object> set = new HashSet<>();
                    for (Object obj : list) {
                        Map<String, Object> hierarchy = (Map<String, Object>) obj;
                        product.put("category_id" ,hierarchy.get("id"));
                        List<Object> labels = (List<Object>) hierarchy.get("label");
                        List<Object> categoryIds = (List<Object>) hierarchy.get("path");
                        for (int inner = 0; inner < categoryIds.size(); inner++) {
                            if (set.add(categoryIds.get(inner))) {
                                catLabels.append(labels.get(inner)).append(",");
                                catIds.append(categoryIds.get(inner)).append(",");

                                final int temp = inner;
                                // leaf category info
                                if (categoryIds.size() - 1 == inner) {
                                    product.compute(categoryLevelMap.get(0)[0],
                                            (k, v) -> v == null ? labels.get(temp) : v + "," + labels.get(temp));
                                    product.compute(categoryLevelMap.get(0)[1], (k,
                                                                                 v) -> v == null ? categoryIds.get(temp) : v + "," + categoryIds.get(temp));
                                }
                                // extra if, because leaf category can also be on level 1/2/3
                                if (inner == 1 || inner == 2 || inner == 3) {
                                    // level one, two and three category info
                                    product.compute(categoryLevelMap.get(temp)[0],
                                            (k, v) -> v == null ? labels.get(temp) : v + "," + labels.get(temp));
                                    product.compute(categoryLevelMap.get(temp)[1], (k,
                                                                                    v) -> v == null ? categoryIds.get(temp) : v + "," + categoryIds.get(temp));
                                }
                            }
                        }
                    }
                    if (catLabels.length() > 0) {
                        product.put("category_names", catLabels.substring(0, catLabels.length() - 1));
                        product.put("category_ids", catIds.substring(0, catIds.length() - 1));
                    }
                }
                // images path handling
                for (String ac : imageAttrCodes) {
                    Object img = product.get(ac);
                    if (img != null)
                        product.put(ac, imageUrlPrefix + img);
                }
                // creating child skus and quantities string for configurable and bundle
                // products
                if (prdductType.equalsIgnoreCase("configurable") || prdductType.equalsIgnoreCase("bundle")) {
                    list = (List<Object>) product
                            .remove(prdductType.equalsIgnoreCase("configurable") ? "configs" : "bundle_options");
                    if (list != null) {
                        final StringBuilder childItemQty = new StringBuilder();
                        String childSkus = list.stream().filter(Objects::nonNull).map(o -> (Map<String, Object>) o)
                                .filter(o -> Objects.nonNull(o.get("sku"))).map(o -> {
                                    String sku = o.get("sku").toString();
                                    childItemQty.append(sku).append(":").append(o.get("quantity")).append(",");
                                    return sku;
                                }).collect(Collectors.joining(","));
                        product.put("child_skus", childSkus);
                        if (prdductType.equalsIgnoreCase("bundle") && childItemQty.length() > 0)
                            product.put("child_item_qty", childItemQty.substring(0, childItemQty.length() - 1));
                    }
                }
                // put true or false value for boolean attr codes

                // other mappings
                yesOrNoAttributes.stream().filter(a -> product.get(a) != null).forEach(atrCode -> {
                    Object o = product.get(atrCode);
                    product.put(atrCode, o.toString().trim().equals("1") ? "Yes" : "No");
                });
                product.put("store_id", storeId);
            } catch (Exception e) {
                log.error("Exception while converting data for product row id :[{}] \n{}", product.get("row_id"),
                        ExceptionUtils.getStackTrace(e));
            }

        }
        ListingResponse response = ListingResponseCreaterUtil.createResponse(searchResult.getData(),
                searchResult.getPageInfo().getTotalRecords(), searchResult.getPageInfo().getTotalPages());
        if (userConfig != null && userConfig.getDefaultUserConfiguration() != null)
            response.setDefaultUserConfig(userConfig.getDefaultUserConfiguration().getConfig());
        if (userConfig != null && userConfig.getAllUserConfigurations() != null)
            response.setUserConfigList(userConfig.getAllUserConfigurations());
        response.setStatus(new Status(searchResult.getStatus()));
        if (response.getMetadata() == null)
            response.setMetadata(new LinkedHashMap<>());
        return response;
    }

    private Pageable handlePagingAndSorting(Pageable pageable, Map<String, Object> filtersMap) {
        String ksaAttribute = null;
        if (ObjectUtils.isNotEmpty(filtersMap)) {
            filtersMap.remove("direction");
            Object removedObj = filtersMap.remove("size");
            int recordsPerPage = removedObj == null ? Constants.DEFAULT_RECORDS_PER_PAGE : (Integer) removedObj;
            removedObj = filtersMap.remove("page");
            int pageNumber = Constants.DEFAULT_PAGE_NUMBER;
            if (removedObj != null) {
                pageNumber = Integer.parseInt(removedObj.toString());
                if (pageNumber > 0) {
                    pageNumber = pageNumber - 1;
                }
            }
            String[] sortingProperties = new String[] {};
            Object sortInFilter = filtersMap.remove("sort");
            Sort sort = pageable.getSort();
            if (sortInFilter != null) {
                sortingProperties = sortInFilter.toString().split(",");
                ksaAttribute = sortingProperties[0];
                if (sortPropertyReplacementMap.get(sortingProperties[0]) != null) {
                    sortingProperties[0] = sortPropertyReplacementMap.get(sortingProperties[0]);
                }
                sort = Sort.by(Sort.Direction.valueOf(sortingProperties[1].toUpperCase()), sortingProperties[0]);
            }

            setLocale(filtersMap, ksaAttributes.contains(ksaAttribute));
            return PageRequest.of(pageNumber, recordsPerPage, sort);
        }
        setLocale(filtersMap, false);
        return pageable;
    }

    private void setLocale(Map<String, Object> filters, boolean notKW) {
        boolean notKwLocale = notKW ? true
                : filters.keySet().stream().anyMatch(s -> ksaAttributes.contains(s.toString()));
        Object store = filters.remove("store_id");
        int storeId = 0; // default store
        if (store != null)
            storeId = Integer.parseInt(store.toString().trim());
        LocaleContextHolder.setLocale(LocaleStore.of(storeId, notKwLocale ? "sa" : "kw"));
    }

    SearchProductResponseDTO searchProductsInOctopus(final SearchProductRequestDto request) {
        SearchProductResponseDTO searchResult = null;
        try {
            final String json = JsonUtils.encode(request);
            log.info("Search Request : {}", json);
            searchResult = octopusProductSearchService.searchProducts(json);
            log.info("Response received from octopus");
        } catch (Throwable e) {
            log.error("Got error while calling octopus {}", e.getMessage());
            SearchProductResponseDTO error = null;
            try {
                error = JsonUtils.decode(e.getMessage(), SearchProductResponseDTO.class);
            } catch (Exception ex) {
            }
            throw new GenericException(
                    error == null ? "Something went wrong on octopus" : error.getErrorResponse().getErrorMessage());
        }
        return searchResult;
    }
}
