package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.common.AdminUser;
import com.boutiqaat.catalogadminexportimportplus.domain.UserConfigurationCreateOrUpdateService;
import com.boutiqaat.catalogadminexportimportplus.entity.UserConfiguration;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class UserConfigurationUtil {

    @Autowired
    private UserConfigurationCreateOrUpdateService userConfigurationCreateOrUpdateService;

    @Data
    public class UserConfigResponse {
        private List<UserConfiguration> allUserConfigurations;
        private UserConfiguration defaultUserConfiguration;
    }






}
