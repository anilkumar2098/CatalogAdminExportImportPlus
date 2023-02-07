package com.boutiqaat.catalogadminexportimportplus.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@DynamicUpdate
@Entity
@Table(schema = "boutiqaat_catalog", name="catalog_product_flat")
@NoArgsConstructor
@AllArgsConstructor
public class CatalogProductFlat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Integer id;

    @Column(name="accessories_material")
    private Integer accessoriesMaterial;

    @Column(name="accessories_size")
    private Integer accessoriesSize;

    private Integer active;

    private Integer activity;

    @Column(name="allow_message")
    private Integer allowMessage;

    @Column(name="allow_open_amount")
    private Integer allowOpenAmount;

    @Column(name="attribute_set_id")
    private Integer attributeSetId;

    @Column(name="attribute_set_name", length=255)
    private String attributeSetName;

    @Column(name="bag_type")
    private Integer bagType;

    @Column(name="bar_code", length=255)
    private String barCode;

    @Column(length=255)
    private String benefits;

    @Column(name="bottle_size")
    private Integer bottleSize;

    @Column(name="boutiqaat_homepicks")
    private Integer boutiqaatHomepicks;

    private Integer box;

    @Column(name="brand_arabic_name", length=255)
    private String brandArabicName;

    @Column(name="brand_id")
    private Integer brandId;

    @Column(name="brand_name", length=255)
    private String brandName;

    @Column(name="btq_color")
    private Integer btqColor;

    @Column(name="bundle_type")
    private Integer bundleType;

    @Column(name="canonical_cross_domain")
    private Integer canonicalCrossDomain;

    @Lob
    @Column(name="canonical_url")
    private String canonicalUrl;

    @Column(name="casual_sports_active", length=255)
    private String casualSportsActive;

    @Column(name="category_for_filter")
    private Integer categoryForFilter;

    @Column(name="category_ids", length=255)
    private String categoryIds;

    @Column(name="category_names", length=1000)
    private String categoryNames;

    @Column(name="category_type", length=255)
    private String categoryType;

    private Integer color;

    @Column(name="color_family")
    private Integer colorFamily;

    @Lob
    @Column(name="comment_box")
    private String commentBox;

    @Column(length=255)
    private String concerns;

    @Column(precision=10, scale=6)
    private BigDecimal cost;

    @Column(name="country_exclude", length=255)
    private String countryExclude;

    @Column(name="country_of_manufacture", length=255)
    private String countryOfManufacture;

    private Integer coverage;

    @Column(name="created_at", nullable=false)
    private Timestamp createdAt;

    @Column(name="created_in")
    private BigInteger createdIn;

    @Column(name="custom_design", length=255)
    private String customDesign;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="custom_design_from")
    private Date customDesignFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="custom_design_to")
    private Date customDesignTo;

    @Column(name="custom_duty_ae", precision=10, scale=6)
    private BigDecimal customDutyAe;

    @Column(name="custom_duty_bh", precision=10, scale=6)
    private BigDecimal customDutyBh;

    @Column(name="custom_duty_iq", length=255)
    private String customDutyIq;

    @Column(name="custom_duty_kw", precision=10, scale=6)
    private BigDecimal customDutyKw;

    @Column(name="custom_duty_om", precision=10, scale=6)
    private BigDecimal customDutyOm;

    @Column(name="custom_duty_qa", precision=10, scale=6)
    private BigDecimal customDutyQa;

    @Column(name="custom_duty_sa", precision=10, scale=6)
    private BigDecimal customDutySa;

    @Column(name="custom_layout", length=255)
    private String customLayout;

    @Lob
    @Column(name="custom_layout_update")
    private String customLayoutUpdate;

    @Lob
    private String description;

    @Column(name="device_type")
    private Integer deviceType;

    @Column(name="diet_type")
    private Integer dietType;

    @Column(name="dress_length")
    private Integer dressLength;

    @Column(name="dress_size")
    private Integer dressSize;

    @Column(name="ebizmarts_mark_visited")
    private Integer ebizmartsMarkVisited;

    @Column(name="email_template", length=255)
    private String emailTemplate;

    @Column(name="enabling_option")
    private Integer enablingOption;

    @Column(name="exclude_from_crosslinking")
    private Integer excludeFromCrosslinking;

    @Column(name="exclude_from_html_sitemap")
    private Integer excludeFromHtmlSitemap;

    @Column(name="exclude_from_sitemap")
    private Integer excludeFromSitemap;

    private Integer exclusive;

    @Column(name="face_shape")
    private Integer faceShape;

    @Column(name="fashion_material")
    private Integer fashionMaterial;

    private Integer finish;

    private Integer fit;

    private Integer flavor;

    @Column(name="footwear_material")
    private Integer footwearMaterial;

    @Column(name="footwear_size")
    private Integer footwearSize;

    private Integer formulation;

    @Column(name="frame_shape")
    private Integer frameShape;

    @Column(name="free_from")
    private Integer freeFrom;

    @Column(length=255)
    private String gallery;

    @Lob
    private String gender;

    @Column(name="gift_message_available", length=255)
    private String giftMessageAvailable;

    @Column(name="gift_wrapping_available", length=255)
    private String giftWrappingAvailable;

    @Column(name="gift_wrapping_price", precision=10, scale=6)
    private BigDecimal giftWrappingPrice;

    @Column(name="giftcard_amounts", precision=10, scale=6)
    private BigDecimal giftcardAmounts;

    @Column(name="giftcard_type")
    private Integer giftcardType;

    @Column(name="hair_type")
    private Integer hairType;

    @Column(name="heel_type")
    private Integer heelType;

    @Column(length=255)
    private String image;

    @Column(name="image_label", length=255)
    private String imageLabel;

    @Column(name="is_featured")
    private Integer isFeatured;

    @Column(name="is_new")
    private Integer isNew;

    @Column(name="is_outlet")
    private Integer isOutlet;

    @Column(name="is_recurring")
    private Integer isRecurring;

    @Column(name="is_redeemable")
    private Integer isRedeemable;

    @Column(name="is_returnable", length=255)
    private String isReturnable;

    @Column(name="is_trending")
    private Integer isTrending;

    @Column(name="jewelry_type")
    private Integer jewelryType;

    @Column(name="korean_beauty")
    private Integer koreanBeauty;

    @Column(name="leaf_category_id", length=255)
    private String leafCategoryId;

    @Column(name="leaf_category_name", length=255)
    private String leafCategoryName;

    @Column(name="lens_treatment")
    private Integer lensTreatment;

    @Column(name="lenses_power")
    private Integer lensesPower;

    private Integer lifetime;

    @Column(name="links_exist")
    private Integer linksExist;

    @Column(name="links_purchased_separately")
    private Integer linksPurchasedSeparately;

    @Column(name="links_title", length=255)
    private String linksTitle;

    @Column(name="lp_custom")
    private Integer lpCustom;

    @Column(name="lp_custom_category")
    private Integer lpCustomCategory;

    @Column(name="makeup_shades")
    private Integer makeupShades;

    @Column(name="makeup_size")
    private Integer makeupSize;

    private Integer manufacturer;

    @Column(name="media_gallery", length=255)
    private String mediaGallery;

    @Column(name="merchant_center_category")
    private Integer merchantCenterCategory;

    @Column(name="meta_description", length=255)
    private String metaDescription;

    @Lob
    @Column(name="meta_keyword")
    private String metaKeyword;

    @Lob
    @Column(name="meta_robots")
    private String metaRobots;

    @Column(name="meta_title", length=255)
    private String metaTitle;

    @Column(name="minimal_price", precision=10, scale=6)
    private BigDecimal minimalPrice;

    @Column(precision=10, scale=6)
    private BigDecimal msrp;

    @Column(name="msrp_display_actual_price_type", length=255)
    private String msrpDisplayActualPriceType;

    private Integer multipack;

    @Column(length=255)
    private String name;

    private Integer neckline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="news_from_date")
    private Date newsFromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="news_to_date")
    private Date newsToDate;

    @Column(name="nutritional_highlights")
    private Integer nutritionalHighlights;

    @Column(name="old_id")
    private Integer oldId;

    @Column(name="open_amount_max", precision=10, scale=6)
    private BigDecimal openAmountMax;

    @Column(name="open_amount_min", precision=10, scale=6)
    private BigDecimal openAmountMin;

    @Column(name="options_container", length=255)
    private String optionsContainer;

    @Column(name="ordered_qty", length=255)
    private String orderedQty;

    @Column(name="page_layout", length=255)
    private String pageLayout;

    @Column(name="part_of_bundle")
    private Integer partOfBundle;

    @Column(name="part_of_conf")
    private Integer partOfConf;

    private Integer pattern;

    @Column(name="perfume_size")
    private Integer perfumeSize;

    @Column(name="perfume_type")
    private Integer perfumeType;

    @Column(name="phone_type")
    private Integer phoneType;

    @Column(length=255)
    private String preference;

    @Column(precision=10, scale=6)
    private BigDecimal price;

    @Column(name="price_strikethrough")
    private Integer priceStrikethrough;

    @Column(name="price_type")
    private Integer priceType;

    @Column(name="price_view")
    private Integer priceView;

    @Column(name="product_id", nullable=false)
    private Integer productId;

    @Lob
    @Column(name="product_seo_name")
    private String productSeoName;

    @Column(name="quantity_and_stock_status")
    private Integer quantityAndStockStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ready_date")
    private Date readyDate;

    @Lob
    @Column(name="recurring_profile")
    private String recurringProfile;

    @Column(name="related_tgtr_position_behavior")
    private Integer relatedTgtrPositionBehavior;

    @Column(name="related_tgtr_position_limit")
    private Integer relatedTgtrPositionLimit;

    private Integer remarks;

    @Column(name="row_id", nullable=false)
    private Integer rowId;

    @Column(name="samples_title", length=255)
    private String samplesTitle;

    @Column(length=255)
    private String season;

    @Column(name="shipment_type")
    private Integer shipmentType;

    @Column(name="shoes_size")
    private Integer shoesSize;

    @Lob
    @Column(name="short_description")
    private String shortDescription;

    @Column(name="skin_tone")
    private Integer skinTone;

    @Column(name="skin_type")
    private Integer skinType;

    @Column(length=255)
    private String sku;

    @Column(name="sku_type")
    private Integer skuType;

    @Column(name="sleeve_length")
    private Integer sleeveLength;

    @Column(name="small_image", length=255)
    private String smallImage;

    @Column(name="small_image_label", length=255)
    private String smallImageLabel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="special_from_date")
    private Date specialFromDate;

    @Column(name="special_name", length=255)
    private String specialName;

    @Column(name="special_price", precision=10, scale=6)
    private BigDecimal specialPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="special_to_date")
    private Date specialToDate;

    private Integer spf;

    @Column(name="state_filter")
    private Integer stateFilter;

    private Integer status;

    @Column(name="store_id")
    private Integer storeId;

    @Column(name="sunglasses_style")
    private Integer sunglassesStyle;

    @Column(name="supplement_size")
    private Integer supplementSize;

    @Column(name="supplier_color")
    private Integer supplierColor;

    @Column(name="swatch_image", length=255)
    private String swatchImage;

    @Column(name="tax_class_id")
    private Integer taxClassId;

    @Column(length=255)
    private String testak;

    @Column(length=255)
    private String thumbnail;

    @Column(name="thumbnail_label", length=255)
    private String thumbnailLabel;

    @Column(name="tier_price", precision=10, scale=6)
    private BigDecimal tierPrice;

    @Column(name="tools_additional_information", length=255)
    private String toolsAdditionalInformation;

    @Column(name="ts_dimensions_height", precision=10, scale=6)
    private BigDecimal tsDimensionsHeight;

    @Column(name="ts_dimensions_length", precision=10, scale=6)
    private BigDecimal tsDimensionsLength;

    @Column(name="ts_dimensions_width", precision=10, scale=6)
    private BigDecimal tsDimensionsWidth;

    @Column(name="type_id", length=255)
    private String typeId;

    @Column(name="updated_at", nullable=false)
    private Timestamp updatedAt;

    @Column(name="updated_in")
    private BigInteger updatedIn;

    @Column(name="upsell_tgtr_position_behavior")
    private Integer upsellTgtrPositionBehavior;

    @Column(name="upsell_tgtr_position_limit")
    private Integer upsellTgtrPositionLimit;

    @Column(name="url_key", length=255)
    private String urlKey;

    @Column(name="url_path", length=255)
    private String urlPath;

    @Column(name="use_config_allow_message")
    private Integer useConfigAllowMessage;

    @Column(name="use_config_email_template")
    private Integer useConfigEmailTemplate;

    @Column(name="use_config_is_redeemable")
    private Integer useConfigIsRedeemable;

    @Column(name="use_config_lifetime")
    private Integer useConfigLifetime;

    @Column(name="user_name")
    private Integer userName;

    @Column(name="vendor_reference", length=255)
    private String vendorReference;

    @Column(length=255)
    private String video;

    private Integer visibility;

    @Column(name="warranty_type")
    private Integer warrantyType;

    @Column(name="watches_case_material")
    private Integer watchesCaseMaterial;

    @Column(name="watches_movement")
    private Integer watchesMovement;

    @Column(name="watches_strap")
    private Integer watchesStrap;

    @Column(name="watches_style")
    private Integer watchesStyle;

    @Column(name="watches_waterproof")
    private Integer watchesWaterproof;

    @Column(precision=10, scale=6)
    private BigDecimal weight;

    @Column(name="weight_type")
    private Integer weightType;

    @Column(name="why_in_active", length=255)
    private String whyInActive;

    @Column(name="youtube_url", length=255)
    private String youtubeUrl;

    @Column(name = "child_skus")
    private String childSkus;

    @Column(name = "watches_feature")
    private String watchesFeature;

    @Column(name = "is_exclusive_to_celebrity")
    private String isExclusiveToCelebrity;

    @Column(name = "child_item_qty")
    private String childItemQty;

    @Column(name = "level_two_category")
    private String levelTwoCategory;
    @Column(name = "level_three_category")
    private String levelThreeCategory;
    @Column(name = "level_one_category")
    private String levelOneCategory;
    @Column(name = "level_two_category_name")
    private String levelTwoCategoryName;
    @Column(name = "level_three_category_name")
    private String levelThreeCategoryName;
    @Column(name = "level_one_category_name")
    private String levelOneCategoryName;
    @Column(name = "layered_navigation")
    private Integer layeredNavigation;
}
