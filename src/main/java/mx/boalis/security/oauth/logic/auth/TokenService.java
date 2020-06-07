package mx.boalis.security.oauth.logic.auth;

import mx.boalis.security.oauth.beans.OAuthTokenBean;
import mx.boalis.security.oauth.dao.SecuritySessionDao;
import mx.boalis.security.oauth.dao.impl.AppClientsDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;



public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final AppClientsDaoImpl appClientsDaoImpl;
    private final SecuritySessionDao clientSessionDao;

    public TokenService(AppClientsDaoImpl appClientsDaoImpl, SecuritySessionDao clientSessionDao) {
        this.appClientsDaoImpl = appClientsDaoImpl;
        this.clientSessionDao = clientSessionDao;
    }


    public boolean hasAnotherSecuritySession(String app,String subject){
        return this.clientSessionDao.exists(app,subject);
    }

    public boolean areCredentialsValid(String app,String clientId,String secretClient){
        String daoSecret = this.appClientsDaoImpl.getCredentials(app,clientId);
        return daoSecret.equals(secretClient);
    }

    protected String parseSubjectFromToken(String token){
        return "mep@gmail.com";
    }

    public OAuthTokenBean issueTokens(String app,Map<String,String> userData){
        return new OAuthTokenBean("acc_token_1231231231231231", "12345", "ref_token_123412341234","Bearer");
    }
}
