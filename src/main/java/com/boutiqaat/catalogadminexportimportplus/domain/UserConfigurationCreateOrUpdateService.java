package com.boutiqaat.catalogadminexportimportplus.domain;

public interface UserConfigurationCreateOrUpdateService {

    void markAllOtherRowsNotDefault(Long userId, String pageId);
    void markConfigAsDefault(Long configId, Long userId, String pageId);
}
