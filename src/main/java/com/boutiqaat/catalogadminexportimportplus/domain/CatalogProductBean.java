package com.boutiqaat.catalogadminexportimportplus.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@JsonDeserialize
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CatalogProductBean implements Serializable, Comparable<CatalogProductBean> {

    private static final long serialVersionUID = 1L;

    public CatalogProductBean(Integer productId, Integer rowId, Integer storeId) {
        super();
        this.productId = productId;
        this.rowId = rowId;
        this.storeId = storeId;
    }

    private Integer accessoriesMaterial;
    private Integer accessoriesSize;
    private Integer active;
    private Integer activity;
    private Integer allowMessage;
    private Integer allowOpenAmount;
    private Integer attributeSetId;
    private String attributeSetName;
    private Integer bagType;
    private String barCode;
    private String benefits;
    private Integer bottleSize;
    private Integer boutiqaatHomepicks;
    private Integer box;
    private String brandArabicName;
    private Integer brandId;
    private String brandName;
    private Integer btqColor;
    private Integer bundleType;
    private Integer canonicalCrossDomain;
    private String canonicalUrl;
    private String casualSportsActive;
    private Integer categoryForFilter;
    private String categoryIds;
    private String categoryNames;
    private String categoryType;
    private Integer color;
    private Integer colorFamily;
    private String commentBox;
    private String concerns;
    private BigDecimal cost;
    private String countryExclude;
    private String countryOfManufacture;
    private Integer coverage;
    private Timestamp createdAt;
    private Long createdIn;
    private String customDesign;
    private Date customDesignFrom;
    private Date customDesignTo;
    private BigDecimal customDutyAe;
    private BigDecimal customDutyBh;
    private String customDutyIq;
    private BigDecimal customDutyKw;
    private BigDecimal customDutyOm;
    private BigDecimal customDutyQa;
    private BigDecimal customDutySa;
    private String customLayout;
    private String customLayoutUpdate;
    private String description;
    private Integer deviceType;
    private Integer dietType;
    private Integer dressLength;
    private Integer dressSize;
    private Integer ebizmartsMarkVisited;
    private String emailTemplate;
    private Integer enablingOption;
    private Integer excludeFromCrosslinking;
    private Integer excludeFromHtmlSitemap;
    private Integer excludeFromSitemap;
    private Integer exclusive;
    private Integer faceShape;
    private Integer fashionMaterial;
    private Integer finish;
    private Integer fit;
    private Integer flavor;
    private Integer footwearMaterial;
    private Integer footwearSize;
    private Integer formulation;
    private Integer frameShape;
    private Integer freeFrom;
    private String gallery;
    private String gender;
    private String giftMessageAvailable;
    private String giftWrappingAvailable;
    private BigDecimal giftWrappingPrice;
    private BigDecimal giftcardAmounts;
    private Integer giftcardType;
    private Integer hairType;
    private Integer heelType;
    private String image;
    private String imageLabel;
    private Integer isFeatured;
    private Integer isNew;
    private Integer isOutlet;
    private Integer isRecurring;
    private Integer isRedeemable;
    private String isReturnable;
    private Integer isTrending;
    private Integer jewelryType;
    private Integer koreanBeauty;
    private String leafCategoryId;
    private String leafCategoryName;
    private Integer lensTreatment;
    private Integer lensesPower;
    private Integer lifetime;
    private Integer linksExist;
    private Integer linksPurchasedSeparately;
    private String linksTitle;
    private Integer lpCustom;
    private Integer lpCustomCategory;
    private Integer makeupShades;
    private Integer makeupSize;
    private Integer manufacturer;
    private String mediaGallery;
    private Integer merchantCenterCategory;
    private String metaDescription;
    private String metaKeyword;
    private String metaRobots;
    private String metaTitle;
    private BigDecimal minimalPrice;
    private BigDecimal msrp;
    private String msrpDisplayActualPriceType;
    private Integer multipack;
    private String name;
    private Integer neckline;
    private Date newsFromDate;
    private Date newsToDate;
    private Integer nutritionalHighlights;
    private Integer oldId;
    private BigDecimal openAmountMax;
    private BigDecimal openAmountMin;
    private String optionsContainer;
    private String orderedQty;
    private String pageLayout;
    private Integer partOfBundle;
    private Integer partOfConf;
    private Integer pattern;
    private Integer perfumeSize;
    private Integer perfumeType;
    private Integer phoneType;
    private String preference;
    private BigDecimal price;
    private Integer priceStrikethrough;
    private Integer priceType;
    private Integer priceView;
    private Integer productId;
    private String productSeoName;
    private Integer quantityAndStockStatus;
    private Date readyDate;
    private String recurringProfile;
    private Integer relatedTgtrPositionBehavior;
    private Integer relatedTgtrPositionLimit;
    private Integer remarks;
    private Integer rowId;
    private String samplesTitle;
    private String season;
    private Integer shipmentType;
    private Integer shoesSize;
    private String shortDescription;
    private Integer skinTone;
    private Integer skinType;
    private String sku;
    private Integer skuType;
    private Integer sleeveLength;
    private String smallImage;
    private String smallImageLabel;
    private Date specialFromDate;
    private String specialName;
    private BigDecimal specialPrice;
    private Date specialToDate;
    private Integer spf;
    private Integer stateFilter;
    private Integer status;
    private Integer storeId;
    private Integer sunglassesStyle;
    private Integer supplementSize;
    private Integer supplierColor;
    private String swatchImage;
    private Integer taxClassId;
    private String testak;
    private String thumbnail;
    private String thumbnailLabel;
    private BigDecimal tierPrice;
    private String toolsAdditionalInformation;
    private BigDecimal tsDimensionsHeight;
    private BigDecimal tsDimensionsLength;
    private BigDecimal tsDimensionsWidth;
    private String typeId;
    private Timestamp updatedAt;
    private Long updatedIn;
    private Integer upsellTgtrPositionBehavior;
    private Integer upsellTgtrPositionLimit;
    private String urlKey;
    private String urlPath;
    private Integer useConfigAllowMessage;
    private Integer useConfigEmailTemplate;
    private Integer useConfigIsRedeemable;
    private Integer useConfigLifetime;
    private Integer userName;
    private String vendorReference;
    private String video;
    private Integer visibility;
    private Integer warrantyType;
    private Integer watchesCaseMaterial;
    private Integer watchesMovement;
    private Integer watchesStrap;
    private Integer watchesStyle;
    private Integer watchesWaterproof;
    private BigDecimal weight;
    private Integer weightType;
    private String whyInActive;
    private String youtubeUrl;
    private String childSkus;
    private String watchesFeature;
    private String isExclusiveToCelebrity;
    public String childItemQty;
    private String levelTwoCategory;
    private String levelThreeCategory;
    private String levelOneCategory;
    private String levelTwoCategoryName;
    private String levelThreeCategoryName;
    private String levelOneCategoryName;
    private Integer layeredNavigation;
    @Override
    public int compareTo(CatalogProductBean o) {
        if (o == null)
            return -1;
        int productIdComparision = this.getProductId().compareTo(o.getProductId());
        int storeIdComparision = this.getStoreId().compareTo(o.getStoreId());
        if (productIdComparision == 0)
            return (storeIdComparision == 0) ? this.getRowId().compareTo(o.getRowId()) : storeIdComparision;
        else
            return productIdComparision;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        result = prime * result + ((rowId == null) ? 0 : rowId.hashCode());
        result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CatalogProductBean other = (CatalogProductBean) obj;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        if (rowId == null) {
            if (other.rowId != null)
                return false;
        } else if (!rowId.equals(other.rowId))
            return false;
        if (storeId == null) {
            if (other.storeId != null)
                return false;
        } else if (!storeId.equals(other.storeId))
            return false;
        return true;
    }

}

