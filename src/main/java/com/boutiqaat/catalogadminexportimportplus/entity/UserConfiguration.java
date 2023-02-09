package com.boutiqaat.catalogadminexportimportplus.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.boutiqaat.catalogadminexportimportplus.entity.UserConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "user_configuration")
@Table(schema = "boutiqaat_catalog", name = "user_configuration" , uniqueConstraints=
@UniqueConstraint(columnNames={"name", "user_id", "page_id"})
)
@Data
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
@DynamicUpdate
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "page_id")
    @JsonProperty(value = "page_id")
    private String pageId;

    @Column(name = "user_id")
    @JsonProperty(value = "user_id")
    private Long userId;

    @Type(type = "json")
    @Column(name = "config", columnDefinition = "json")
    @Convert(attributeName = "data", converter = JsonToUserConfigConverter.class)
    private UserConfig config;

    @Column(name = "is_default")
    @JsonProperty(value = "is_default")
    private Boolean isDefault;

    @Data
    @NoArgsConstructor
    public static class UserConfig implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        @JsonSerialize
        private Set<String> columns = new HashSet<>();
        @JsonSerialize
        private List<Filter> filters = new ArrayList<>();
        @JsonSerialize
        private Sorting sorting = new Sorting();
        @JsonSerialize
        private List<String> columnsSortingOrder = new ArrayList<>();

        @Data
        @NoArgsConstructor
        public static class Filter implements Serializable {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @JsonSerialize
            @JsonProperty("name")
            private String name;

            @JsonSerialize
            @JsonProperty("value")
            private String value;
        }

        @Data
        @NoArgsConstructor
        public static class Sorting implements Serializable {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @JsonSerialize
            @JsonProperty("sort_by")
            private String sortBy;

            @JsonSerialize
            @JsonProperty("sort_direction")
            private String sortDirection;
        }
    }

    // Constructor with id and name, used while returning
    // user configurations object in listing APIs
    public UserConfiguration(Long id, String name, Boolean isDefault){
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
    }

}