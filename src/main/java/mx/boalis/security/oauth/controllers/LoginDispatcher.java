package mx.boalis.security.oauth.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import mx.boalis.security.oauth.beans.ResourceOwnerBean;
import mx.boalis.security.oauth.logic.config.TenantConfigService;
import mx.boalis.security.oauth.logic.auth.ValidateAuthRequestService;
import mx.boalis.security.oauth.dao.LoginSession;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginDispatcher implements Handler {
    private final ValidateAuthRequestService validateAuthRequestService;
    private final TenantConfigService tenantConfigService;
    private final LoginSession session;

    public LoginDispatcher(ValidateAuthRequestService validateAuthRequestService, TenantConfigService tenantConfigService, LoginSession session) {
        this.validateAuthRequestService = validateAuthRequestService;
        this.tenantConfigService = tenantConfigService;
        this.session = session;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String uuid = ctx.pathParam("app");
        String responseType = ctx.queryParam("response_type");
        String clientId = ctx.queryParam("client_id");
        String redirectUri = ctx.queryParam("redirect_uri");
        String scope = ctx.queryParam("scope");
        String state = ctx.queryParam("state");

        ResourceOwnerBean resourceOwnerBean = new ResourceOwnerBean();
        resourceOwnerBean.setClientId(clientId);
        resourceOwnerBean.setCode(null);
        resourceOwnerBean.setRedirectUrl(redirectUri);
        resourceOwnerBean.setState(state);
        resourceOwnerBean.setResponseType(responseType);

        boolean isValid = validateAuthRequestService.validate(resourceOwnerBean,uuid);

        if (!isValid){
            sendError(ctx,"","");
            return;
        }
        // Aqui solo se llega con response_type=code

        Map loginChallenge = new HashMap<String,String>();
        loginChallenge.put("redir",redirectUri);
        loginChallenge.put("client_id",clientId);
        loginChallenge.put("scope",scope);
        loginChallenge.put("state",state);
        loginChallenge.put("type",responseType);


        // Creamos el key
        String challengeIdentifier = UUID.randomUUID().toString();
        session.create(challengeIdentifier,loginChallenge);

        // sesion creada ahora a redireccionar
        String redirectUrl= tenantConfigService.getKey(uuid,"redir");
        String requestRedirect = String.format(redirectUrl,challengeIdentifier);
        ctx.redirect(requestRedirect);

        return;
    }
    private void sendError(@NotNull Context ctx,String error,String description){
        ctx.result(String.format("Error: %s Detail: %s ",error,description));
    }


}
