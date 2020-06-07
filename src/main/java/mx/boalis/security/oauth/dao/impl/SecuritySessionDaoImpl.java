package mx.boalis.security.oauth.dao.impl;

import mx.boalis.security.oauth.dao.SecuritySessionDao;

import java.util.Map;

public class SecuritySessionDaoImpl implements SecuritySessionDao {

    @Override
    public void createSessionData(String tenant, String subject, Map<String, String> userData, String accessToken, String refreshToken) {

    }

    @Override
    public boolean exists(String tenant, String subject) {
        return false;
    }

    @Override
    public boolean isUserSessionRevoked(String tenant, String subject) {
        return false;
    }

    @Override
    public Map<String, String> getSessionData(String tenant, String subject) {
        return null;
    }

    @Override
    public void revokeUser(String tenant, String subject) {

    }
}
