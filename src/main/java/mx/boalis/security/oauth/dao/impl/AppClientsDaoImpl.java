package mx.boalis.security.oauth.dao.impl;

import mx.boalis.security.oauth.beans.ClientAppBean;
import mx.boalis.security.oauth.dao.AppClientsDao;

public class AppClientsDaoImpl implements AppClientsDao {
    public ClientAppBean createApp(String name){
        return new ClientAppBean("dummy","werqwhi4jerqpwouei1233","Super-secret_clientSecret007");
    }

    public String getCredentials(String app,String clientId){
        return "xzdqwsuer-secret";
    }
}
