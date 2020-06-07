package mx.boalis.security.oauth.dao.impl;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import mx.boalis.security.oauth.dao.SecuritySessionDao;

import java.util.Map;

public class SecuritySessionDaoHazelcastImpl implements SecuritySessionDao {
    private final HazelcastInstance hz;
    private final Map<String, Map<String,String>> session = null;

    public SecuritySessionDaoHazelcastImpl(HazelcastInstance hz) {
        this.hz = Hazelcast.newHazelcastInstance();
        session = this.hz.getMap("session");
    }

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
