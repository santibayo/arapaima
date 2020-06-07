package mx.boalis.security.oauth.logic.auth;

import mx.boalis.security.oauth.beans.ResourceOwnerBean;
import mx.boalis.security.oauth.dao.LoginSessionDao;
import mx.boalis.security.oauth.logic.config.KeyNotFound;
import mx.boalis.security.oauth.logic.config.TenantConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.*;

public class AuthorizationCodeService {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationCodeService.class);
    private final TenantConfigService config;
    private final LoginSessionDao session;


    public AuthorizationCodeService(TenantConfigService config, LoginSessionDao session) {
        this.config = config;
        this.session = session;
    }

    public final boolean matchesAnyScope(String tenant,String uuid){
        List<String> scopes = this.config.getDeclaredTenantScopes(tenant);
        Map<String,String> sessionData = session.get(uuid);
        String [] reqScopes = sessionData.get("scope").split(" ");
        for (String reqScope: reqScopes){
            if (scopes.contains(reqScope)){
                return true;
            }
        }
        return false;
    }

    public final boolean verifyChallenge(String uuid,String challenge){
        Map<String, String> userData = session.get(uuid);
        if (userData==null) return false;
        String storedChallenge = userData.get("login_challenge");
        if (storedChallenge.equals(challenge)){
            return true;
        }
        return false;
    }
    // TODO improve method
    protected Map<String,String> getUserTokenMap(String tenant,String uuid){
        Map<String, String> userData = session.get(uuid);
        return userData;
    }

    public ResourceOwnerBean getAuthorizationCode(String tenant, String uuid)throws KeyNotFound {
        String issuer = this.config.getIssuer(tenant);
        if (issuer==null){
            throw new KeyNotFound();
        }
        Map<String,String> tokenData = this.getUserTokenMap(tenant,uuid);
        tokenData.put("issuer",issuer);
        String code = this.generateAuthorizationCode();
        tokenData.put("code",code);
        session.linkCode(code,uuid);
        ResourceOwnerBean resourceOwnerBean = new ResourceOwnerBean();
        resourceOwnerBean.setResponseType(tokenData.get("type"));
        resourceOwnerBean.setState(tokenData.get("state"));
        resourceOwnerBean.setRedirectUrl(tokenData.get("redir"));
        resourceOwnerBean.setCode(code);
        return resourceOwnerBean;
    }

    protected String generateAuthorizationCode(){
        SecureRandom number = new SecureRandom();
        return String.valueOf(number.nextInt(1000000));
    }

    protected void avoidAditionalRequest(String uuid){
        Map<String, String> userData = session.get(uuid);
        if (userData!=null) {
            userData.remove("login_challenge");
            session.update(uuid,userData);
        }
    }

    public final String generateRedirectUrl(ResourceOwnerBean resourceOwnerBean){
        String url = null;
        try {
            url = resourceOwnerBean.getRedirectUrl()+"?state="+ URLEncoder.encode(resourceOwnerBean.getState(),
                    "UTF-8")+"&authorization_code="+URLEncoder.encode(resourceOwnerBean.getCode(),
                    "UTF-8");
            logger.info(String.format("redirect url = %s",url) );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }




}
