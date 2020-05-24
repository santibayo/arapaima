package mx.boalis.security.oauth.logic.config;

import java.util.List;
import java.util.Map;

public interface TenantConfigService {
    public String getLoginUrl(String tenant);
    public boolean aprobeScope(String tenant);
    public String getKey(String tenant, String key)throws KeyNotFound;
    public List<String> getDeclaredTenantScopes(String tenant);
    public Map<String,String> getMapping(String tenant);
    public String getIssuer(String tenant);
    public boolean verifyCredencials(String app,String clientID,String clientSecret);
    public String getClientId(String tenant) ;
    public String getClientUrl(String tenant);
    public boolean hasMultipleSessionsAllowed(String tenant);

}
