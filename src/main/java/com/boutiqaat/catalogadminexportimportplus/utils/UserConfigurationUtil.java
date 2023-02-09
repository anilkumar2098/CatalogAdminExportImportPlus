package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.entity.UserConfiguration;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserConfigurationUtil {

    @Data
    public class UserConfigResponse {
        private List<UserConfiguration> allUserConfigurations;
        private UserConfiguration defaultUserConfiguration;
    }

    public UserConfigResponse fetchUserConfigurations(String pageId, Long userConfigId){
        UserConfigResponse userConfigResponse = new UserConfigResponse();
        Boolean willDefaultUserConfigBeDefault = false;
        if(!ObjectUtils.isEmpty(userConfigId)){
            // if userConfigId = -1, then remove any default config
            // present in the system.
            if(userConfigId.equals(-1L)){
                willDefaultUserConfigBeDefault = true;
                userConfigurationCreateOrUpdateService.markAllOtherRowsNotDefault(getAdminUserId(),pageId);
            } else {
                // mark this user config with ID as default because this is passed in
                // the request.
                userConfigurationCreateOrUpdateService.markConfigAsDefault(userConfigId, getAdminUserId(),pageId);
            }
        }
        List<UserConfiguration> userConfigurations = getUserConfigurations(pageId, willDefaultUserConfigBeDefault);
        userConfigResponse.setAllUserConfigurations(userConfigurations.stream().
                map(config -> new UserConfiguration(config.getId(), config.getName(), config.getIsDefault())).
                collect(Collectors.toList()));
        userConfigResponse.setDefaultUserConfiguration(getDefaultUserConfig(userConfigurations, pageId));
        return userConfigResponse;
    }
}
