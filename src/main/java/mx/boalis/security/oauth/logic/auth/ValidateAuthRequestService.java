package mx.boalis.security.oauth.logic.auth;

import mx.boalis.security.oauth.beans.ResourceOwnerBean;
import mx.boalis.security.oauth.logic.config.TenantConfigService;

public class ValidateAuthRequestService {
    private final TenantConfigService configService;

    public ValidateAuthRequestService(TenantConfigService configService) {
        this.configService = configService;
    }


    public boolean validate(ResourceOwnerBean bean,String tenant){
        String sourceUrl = bean.getRedirectUrl();
        if (!urlMatches(sourceUrl,tenant)){
            return false;
        }
        String configTenantClientId = configService.getClientId(tenant);
        if(!configTenantClientId.equals(bean.getClientId())){
            return false;
        }



        return true;
    }

    public final boolean urlMatches(String source,String tenant){
        if (source.equals(configService.getClientUrl(tenant))){
            return true;
        }
        return false;
    }

}
