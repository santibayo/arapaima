package mx.boalis.security.oauth;

import io.javalin.Javalin;
import mx.boalis.security.oauth.controllers.*;
import mx.boalis.security.oauth.logic.auth.AuthorizationCodeService;
import mx.boalis.security.oauth.logic.auth.TokenService;
import mx.boalis.security.oauth.logic.config.TenantConfigService;
import mx.boalis.security.oauth.logic.config.HardCodedTenantConfigService;
import mx.boalis.security.oauth.logic.auth.ValidateAuthRequestService;
import mx.boalis.security.oauth.dao.HashMapSession;
import mx.boalis.security.oauth.dao.LoginSession;

public class Server {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        // Services
        TenantConfigService tenantConfigService = new HardCodedTenantConfigService();
        ValidateAuthRequestService validateAuthRequestService = new ValidateAuthRequestService(tenantConfigService);
        LoginSession session = new HashMapSession();
        AuthorizationCodeService authorizationCodeService= new AuthorizationCodeService(tenantConfigService,session);
        TokenService tokenService = new TokenService(appClientsDao, clientSessionDao);
        // controllers
        app.get("/:app/auth", new LoginDispatcher(validateAuthRequestService, tenantConfigService,session));
        app.post("/:app/load", new LoginOutOfBound(session));
        app.get("/:app/verify", new LoginVerifier(tenantConfigService, authorizationCodeService));
        app.post("/:app/token", new TokenIssuer(tokenService, session,tenantConfigService));
        app.get("/:app/session/:what/:key", new SecuritySession());

    }

}
