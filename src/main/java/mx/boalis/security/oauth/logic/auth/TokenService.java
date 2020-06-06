package mx.boalis.security.oauth.logic.auth;

import mx.boalis.security.oauth.beans.OAuthTokenBean;
import mx.boalis.security.oauth.dao.AppClientsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final AppClientsDao appClientsDao;

    public TokenService(AppClientsDao appClientsDao) {
        this.appClientsDao = appClientsDao;
    }


    public boolean hasAnotherSecuritySession(String subject){
        return false;
    }

    public boolean areCredentialsValid(String clientId,String secretClient){
        return true;
    }

    protected String parseSubjectFromToken(String token){
        return "mep@gmail.com";
    }

    public OAuthTokenBean issueTokens(Map<String,String> userData){
        return new OAuthTokenBean("acc_token_1231231231231231", "12345", "ref_token_123412341234","Bearer");
    }
}
