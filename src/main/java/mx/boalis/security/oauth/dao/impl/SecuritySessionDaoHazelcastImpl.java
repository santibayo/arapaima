package mx.boalis.security.oauth.dao.impl;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import mx.boalis.security.oauth.beans.SessionBean;
import mx.boalis.security.oauth.dao.SecuritySessionDao;
import java.util.concurrent.TimeUnit;

public class SecuritySessionDaoHazelcastImpl implements SecuritySessionDao {
    private final HazelcastInstance hz;
    private final IMap<String, SessionBean> session;
    private final TimeUnit timeUnit;
    private final long ttl;

    public SecuritySessionDaoHazelcastImpl(HazelcastInstance hz) {
        this.hz = Hazelcast.newHazelcastInstance();
        session = this.hz.getMap("session");
        timeUnit = TimeUnit.MINUTES;
        ttl = 120;
    }
    public String getKey(String tenant,String subject){
        return tenant+"__"+subject;
    }


    @Override
    public void createSessionData(String tenant, String subject, String accessToken, String refreshToken) {
        SessionBean sessionBean = new SessionBean();
        sessionBean.setAccessToken(accessToken);
        sessionBean.setRefreshToken(refreshToken);
        sessionBean.setRevoked(false);
        session.put(this.getKey(tenant, subject),sessionBean);
    }

    @Override
    public boolean exists(String tenant, String subject) {
        return session.containsKey(this.getKey(tenant,subject));
    }

    @Override
    public boolean isUserSessionRevoked(String tenant, String subject) {
        if (exists(tenant, subject)){
            return session.get(this.getKey(tenant, subject)).isRevoked();
        }
        return false;
    }

    @Override
    public void revokeUser(String tenant, String subject) {
        if (exists(tenant, subject)){
            SessionBean sessionBean = session.get(this.getKey(tenant, subject));
            sessionBean.setRevoked(true);
            session.put(this.getKey(tenant, subject),sessionBean);
        }
    }
}
