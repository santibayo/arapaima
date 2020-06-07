package mx.boalis.security.oauth.dao.impl;

import mx.boalis.security.oauth.dao.LoginSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HashMapSessionDaoImpl implements LoginSessionDao {
    private final Map session = new HashMap<String,Map<String,String>>();
    private final Logger logger = LoggerFactory.getLogger(HashMapSessionDaoImpl.class);
    @Override
    public void create(String uuid, Map<String, String> challengeData) {
        session.put(uuid,challengeData);
        logger.info("session created!");
        logger.info(String.format("uuid: %s => %s",uuid,session.toString()));
    }

    @Override
    public void update(String uuid, Map<String, String> loginData) {
        loginData.remove("state");
        loginData.remove("scope");
        loginData.remove("client_id");
        loginData.remove("redir");
        Map<String,String> data = (Map<String, String>) session.get(uuid);
        data.putAll(loginData);
        session.replace(uuid,data);
        logger.info("session updated!");
        logger.info(String.format("uuid: %s => %s",uuid,session.toString()));

    }

    @Override
    public Map<String, String> get(String uuid) {
        return (Map<String, String>) this.session.get(uuid);
    }

    @Override
    public void clear(String uuid) {
        this.session.remove(uuid);
        logger.info("session updated!");
        logger.info(String.format("uuid: %s => %s",uuid,session.toString()));

    }

    @Override
    public boolean verify(String uuid) {
        return session.containsKey(uuid);
    }

    @Override
    public void linkCode(String code, String uuid) {
        session.put("login_"+code,uuid);
    }

    @Override
    public String getLoginKeyByCode(String code) {
        String uuid = (String)session.get("login_"+code);
        if (uuid==null){
            return null;
        }
        session.remove("login_"+code);
        return uuid;
    }


}
