package com.boutiqaat.catalogadminexportimportplus.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.boutiqaat.catalogadminexportimportplus.model.Inventory;
import com.boutiqaat.catalogadminexportimportplus.model.Range;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.boutiqaat.catalogadminexportimportplus.model.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
public class SearchProductRequestDto {

    public SearchProductRequestDto() {
        this.activeOnly = false;
    }

    private Locale locale;

    @JsonProperty("include_source")
    private Collection<String> includeSource;

    @JsonProperty("type")
    private String productType; // products type like simple, bundle etc all should be in capital letters

    @JsonProperty("types")
    private Collection<String> productTypes; // same as above productType but it accepts array of product types

    @JsonProperty("visibility")
    private Visibility visibility; // like ALL, CATALOG, SEARCH

    @JsonProperty("exclusiveTo")
    private String exclusiveTo;  // Exclusive to celebrity id

    @JsonProperty("attributes")
    private Map<String, Object> attributes;		// product attributes

    @JsonProperty("product_ids")
    private Set<String> productIds;

    @JsonProperty("product_skus")
    private Set<String> productSkus;

    @JsonProperty("categories")
    private Set<String> categories;		// these are categories id at any level

    @JsonProperty("celebrities")
    private Set<String> celebrities;	// active celebrities id

    @JsonProperty("manufacturers")
    private Set<String> manufacturers;	// brands id

    @JsonProperty("active_only")
    private boolean activeOnly;			// if false return all products active / inactive

    @JsonProperty("size")
    private int size;					// number of records per page

    @JsonProperty("page")
    private int page;					// page number

    @JsonProperty("ranges")
    private Map<String, Range> ranges;	// Ranges (price, selling_price, discount, stocks, product_ids)

    @JsonProperty("inventory")
    private Inventory inventory;		// country[ ae, bh, iq, jo, kw, om, qa, sa ] and warehouse id

    @JsonProperty("device_id")
    private String deviceId;			// This is device id

    @JsonProperty("gender")
    private String gender;				// This is device id

    @JsonProperty("currency_display")
    private String currencyDisplay;  	// this attribute is used to get display / get the correct price and currency based on the country.

    @JsonProperty("query")
    private String query;				// will work like contains search on name,short_description,description,category_names,brand_arabic_name,vendor_reference,brand_name

    @JsonProperty("sort")
    private Map<String, String> sort;	// sort object contains attribute as a key and Asc/Desc as value, attribute could be product name, id, colour, attribute set

    public void setCategories(Set<String> cat) {
        if(this.categories == null)
            this.categories = cat;
        else
            this.categories.addAll(cat);
    }

    /**
     * This method is used to set Sorting properties required by Octopus.</BR>
     * Change sorting direction to Asc / Desc tile case only
     * @param sortInfo
     */
    public void setSort(Sort sortInfo) {
        Order sortingOrder = sortInfo.toList().get(0);
        String direction = sortingOrder.getDirection().name().equals("ASC") ? "Asc" : "Desc";
        sort = Collections.singletonMap(sortingOrder.getProperty(), direction);
    }
}
