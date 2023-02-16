package com.boutiqaat.catalogadminexportimportplus.domain;

import com.boutiqaat.catalogadminexportimportplus.kafka.ExportEvent;

public interface CatalogProductFlatService {

    public String processListingExport(ExportEvent exportEvent);

}
