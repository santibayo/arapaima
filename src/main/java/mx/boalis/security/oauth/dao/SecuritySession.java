package mx.boalis.security.oauth.dao;

import java.util.Map;

public interface SecuritySession {


    public boolean exists(String tenant, String subject);

    public boolean isUserRevoked(String tenant,String subject);

    public Map<String,String> getSessionData(String tenant, String subject);

    public void revokeUser(String tenant,String subject);

}
