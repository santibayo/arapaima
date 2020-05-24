package mx.boalis.security.oauth.logic.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardCodedTenantConfigService implements TenantConfigService {
    @Override


    public String getKey(String tenant, String key) throws KeyNotFound {
        if (key.equals("redir")){
            return getLoginUrl(tenant);
        }
        return null;
    }

    public List<String> getDeclaredTenantScopes(String tenant){
        return Arrays.asList("personal.all","accounts.all","transactions.all");
    };

    public Map<String,String> getMapping(String tenant){
        HashMap<String,String> mappingData = new HashMap<>();
        mappingData.put("subject","uid");
        return mappingData;
    }

    public String getLoginUrl(String tenant){
        return "http://sample.login/?uuid=%s";
    }

    @Override
    public boolean aprobeScope(String tenant) {
        return false;
    }

    public String getIssuer(String tenant){
        return "demo";
    }

    // TODO Cambiar
    @Override
    public boolean verifyCredencials(String app, String clientID, String clientSecret) {
        return false;
    }

    @Override
    public String getClientId(String tenant) {
        return "123";
    }

    @Override
    public String getClientUrl(String tenant) {
        return "http://123.com";
    }

}
