package mx.boalis.security.oauth.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import mx.boalis.security.oauth.beans.OAuthTokenBean;
import mx.boalis.security.oauth.dao.LoginSession;
import mx.boalis.security.oauth.logic.auth.TokenService;
import mx.boalis.security.oauth.logic.config.TenantConfigService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TokenIssuer implements Handler {
    private final Logger logger = LoggerFactory.getLogger(TokenIssuer.class);
    private final TokenService tokenService;
    private final LoginSession loginSession;
    private final TenantConfigService configService;

    public TokenIssuer(TokenService tokenService, LoginSession loginSession, TenantConfigService configService) {
        this.tokenService = tokenService;
        this.loginSession = loginSession;
        this.configService = configService;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String grantType = ctx.formParam("grant_type");
        String clientId = ctx.formParam("client_id");
        String clientSecret = ctx.formParam("client_secret");
        String redirectUri = ctx.formParam("redirect_uri");
        String code = ctx.formParam("code");
        String tenant = ctx.pathParam("app");

        if (grantType.equals("authorization_code")){
            logger.info("request type: authorization code");
            boolean areValidCreds = tokenService.areCredentialsValid(clientId,clientSecret);
            if (!areValidCreds){
                logger.error("Bad credentials request: code = %s, client_id = %s",code,clientId);
                ctx.status(403);
                return;
            }

            String uuid = loginSession.getLoginKeyByCode(code);
            Map<String,String> userData = loginSession.get(uuid);
            loginSession.clear(uuid);
            logger.info(String.format(" performing clean up: uuid = %s, data => %s",uuid,userData.toString()) );
            String subject = userData.get("subject");
            boolean hasMoreSessions = tokenService.hasAnotherSecuritySession(subject);
            if (hasMoreSessions){
                boolean canCreateMultipleSessions = configService.hasMultipleSessionsAllowed(tenant);
                if (!canCreateMultipleSessions){
                    ctx.status(429);
                    return;
                }
            }

            OAuthTokenBean response = tokenService.issueTokens(userData);
            ctx.json(response);

        }


    }


}
