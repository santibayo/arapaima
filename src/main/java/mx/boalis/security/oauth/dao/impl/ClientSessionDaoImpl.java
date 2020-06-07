package mx.boalis.security.oauth.dao.impl;

import mx.boalis.security.oauth.dao.ClientSessionDao;

public class ClientSessionDaoImpl implements ClientSessionDao {
    @Override
    public boolean sessionExists(String app, String subject){
        return false;
    }
}
