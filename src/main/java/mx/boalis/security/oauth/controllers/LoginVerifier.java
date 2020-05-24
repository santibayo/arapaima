package mx.boalis.security.oauth.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import mx.boalis.security.oauth.beans.ResourceOwnerBean;
import mx.boalis.security.oauth.logic.auth.AuthorizationCodeService;
import mx.boalis.security.oauth.logic.config.TenantConfigService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginVerifier implements Handler {
    private final TenantConfigService config;
    private final AuthorizationCodeService authorizationCodeService;
    private final Logger logger = LoggerFactory.getLogger(LoginVerifier.class);

    public LoginVerifier(TenantConfigService config, AuthorizationCodeService authorizationCodeService) {
        this.config = config;
        this.authorizationCodeService = authorizationCodeService;
        logger.info("Login Verifier initializing" );

    }


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String app = ctx.pathParam("app");
        String uuid = ctx.queryParam("uuid");
        String challenge= ctx.queryParam("challenge");
        logger.info(String.format("params: app = %s, uuid = %s, challenge = %s",app,uuid,challenge));
        if (uuid == null || challenge == null){
            ctx.status(400);
            return;
        }
        logger.info("params ok");
        boolean isPreviouslyAuthenticated = authorizationCodeService.verifyChallenge(uuid,challenge);
        if (!isPreviouslyAuthenticated){
            ctx.status(404);
            return;
        }

        logger.info("challenge ok");
        boolean matchScope = authorizationCodeService.matchesAnyScope(app,uuid);

        if (!matchScope) {
            // mostramos error
            ctx.status(404);
            return;
        }
        logger.info("scope ok");

        ResourceOwnerBean resource = authorizationCodeService.getAuthorizationCode(app, uuid);
        String resourceCode = resource.getCode();
        String redirUri = resource.getRedirectUrl();
        String state = resource.getState();
        String code = resource.getCode();
        if (code == null){
            ctx.status(500);
            return;
        }
        logger.info("ready to redir");
        boolean showScope = this.config.aprobeScope(app);
        if (!showScope){
            logger.info("show scope verification false => proceed to callback url");
            String fullUrl= authorizationCodeService.generateRedirectUrl(resource);
            logger.info(String.format(" * Url: %s",fullUrl));
            ctx.redirect(fullUrl);

            return;
        }else{
            // pintar la aprobacion//
            logger.info("aproval ready");

        }

    }
}
