package com.boutiqaat.catalogadminexportimportplus.repositories;

public interface UserConfigurationCustomRepository {

    void markAllOtherRowsNotDefault(Long userId, String pageId);
    void markUserConfigAsDefault(Long userConfigId, Long userId, String pageId);
}
