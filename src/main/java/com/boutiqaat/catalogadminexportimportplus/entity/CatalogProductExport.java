package com.boutiqaat.catalogadminexportimportplus.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * The persistent class for the catalog_product_export database table.
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="catalog_product_export")
@NamedQuery(name="CatalogProductExport.findAll", query="SELECT c FROM CatalogProductExport c")
public class CatalogProductExport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    //@JsonSerialize(using = CustomTimestampSerializer.class)
    @Column(name="export_completed_at")
    private Timestamp exportCompletedAt;

    @Column(name="export_link", length=500)
    private String exportLink;

    //@JsonSerialize(using = CustomTimestampSerializer.class)
    @Column(name="export_started_at")
    private Timestamp exportStartedAt;

    @JsonIgnore
    @Lob
    @Column(name="request_params")
    private String requestParams;

    @Column(length=255)
    private String status;

    @JsonIgnore
    @Column(name="user_id")
    private Long userId;

}
