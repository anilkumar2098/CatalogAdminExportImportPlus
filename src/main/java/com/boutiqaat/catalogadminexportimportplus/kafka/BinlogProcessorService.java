package com.boutiqaat.catalogadminexportimportplus.kafka;

import java.util.List;

public interface BinlogProcessorService {

    List<String> bootstrapAcceptableTypes = List.of(BinlogTypes.BOOTSTRAP_START.getType(),
            BinlogTypes.BOOTSTRAP_INSERT.getType(), BinlogTypes.BOOTSTRAP_COMPLETE.getType());

    void process(ExportEvent binlogMessage);
}
