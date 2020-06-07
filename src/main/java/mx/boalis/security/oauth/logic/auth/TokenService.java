package mx.boalis.security.oauth.logic.auth;

import mx.boalis.security.oauth.beans.OAuthTokenBean;
import mx.boalis.security.oauth.dao.AppClientsDao;
import mx.boalis.security.oauth.dao.ClientSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;



public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final AppClientsDao appClientsDao;
    private final ClientSessionDao clientSessionDao;

    public TokenService(AppClientsDao appClientsDao, ClientSessionDao clientSessionDao) {
        this.appClientsDao = appClientsDao;
        this.clientSessionDao = clientSessionDao;
    }


    public boolean hasAnotherSecuritySession(String app,String subject){
        return this.clientSessionDao.sessionExists(app,subject);
    }

    public boolean areCredentialsValid(String app,String clientId,String secretClient){
        String daoSecret = this.appClientsDao.getCredentials(app,clientId);
        return daoSecret.equals(secretClient);
    }

    protected String parseSubjectFromToken(String token){
        return "mep@gmail.com";
    }

    public OAuthTokenBean issueTokens(String app,Map<String,String> userData){
        return new OAuthTokenBean("acc_token_1231231231231231", "12345", "ref_token_123412341234","Bearer");
    }
}
