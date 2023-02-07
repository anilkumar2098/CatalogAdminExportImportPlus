package com.boutiqaat.catalogadminexportimportplus.kafka;

/*
 * **********************************************************
 *  Copyright ©2015-2022 Boutiqaat. All rights reserved
 *  —————————————————————————————————
 *  NOTICE: All information contained herein is a property of Boutiqaat.
 *  *************************************************************
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public enum BinlogTypes {

    BOOTSTRAP_START("bootstrap-start"),
    BOOTSTRAP_COMPLETE("bootstrap-complete"),
    BOOTSTRAP_INSERT("bootstrap-insert"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    DEFAULT("Not a Sync Operation");

    String type;

    public static BinlogTypes fromString(@NonNull String text) {
        for (BinlogTypes binlog : BinlogTypes.values()) {
            if (binlog.type.equalsIgnoreCase(text)) {
                return binlog;
            }
        }
        return BinlogTypes.DEFAULT;
    }

}
