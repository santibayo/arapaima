package mx.boalis.security.oauth.dao;

import java.util.Map;

public interface SecuritySessionDao {

    public void createSessionData(String tenant,String subject, String accessToken, String refreshToken);

    public boolean exists(String tenant, String subject);

    public boolean isUserSessionRevoked(String tenant,String subject);

    public void revokeUser(String tenant,String subject);

}
