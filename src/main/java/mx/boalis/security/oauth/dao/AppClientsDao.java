package mx.boalis.security.oauth.dao;

import mx.boalis.security.oauth.beans.ClientAppBean;

public class AppClientsDao {
    public ClientAppBean createApp(String name){
        return new ClientAppBean("dummy","werqwhi4jerqpwouei1233","Super-secret_clientSecret007");
    }

    public String getCredentials(String app,String clientId){
        return "xzdqwsuer-secret";
    }
}
