package mx.boalis.security.oauth.dao;

import java.util.Map;

public interface LoginSession {
    public void create(String uuid, Map<String,String> challengeData);
    public void update(String uuid, Map<String,String> loginData);
    public Map<String,String> get(String uuid);
    public void clear(String uuid);
    public boolean verify(String uuid);
    public void linkCode(String code,String uuid);
    public String getLoginKeyByCode(String code);
}
