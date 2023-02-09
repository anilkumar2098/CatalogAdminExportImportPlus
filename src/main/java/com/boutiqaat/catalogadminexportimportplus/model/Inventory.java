package com.boutiqaat.catalogadminexportimportplus.model;


import com.boutiqaat.catalogadminexportimportplus.model.Inventory;

import lombok.Data;

@Data
public class Inventory {


    private Country country;

    private String warehouseId;

    /**
     * Default KW
     * @return
     */
    public Country getCountry() {
        return country == null ? Country.KW : country;
    }

    /**
     * Default 1 ( Kuwait )
     * @return
     */
    public String getWarehouseId() {
        return warehouseId == null ? "1" : warehouseId;
    }

}
