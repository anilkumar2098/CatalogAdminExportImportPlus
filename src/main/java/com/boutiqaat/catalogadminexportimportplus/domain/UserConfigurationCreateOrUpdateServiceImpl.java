package com.boutiqaat.catalogadminexportimportplus.domain;

import com.boutiqaat.catalogadminexportimportplus.repositories.UserConfigurationCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConfigurationCreateOrUpdateServiceImpl implements UserConfigurationCreateOrUpdateService{

    @Autowired
    private UserConfigurationCustomRepository userConfigurationCustomRepository;
    @Override
    public void markAllOtherRowsNotDefault(Long userId, String pageId) {

        userConfigurationCustomRepository.markAllOtherRowsNotDefault(userId, pageId);


    }

    @Override
    public void markConfigAsDefault(Long configId, Long userId, String pageId) {
        userConfigurationCustomRepository.markUserConfigAsDefault(configId, userId, pageId);


    }
}
