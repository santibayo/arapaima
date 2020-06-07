package mx.boalis.security.oauth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import mx.boalis.security.oauth.dao.impl.HashMapSessionDaoImpl;
import mx.boalis.security.oauth.dao.LoginSessionDao;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginOutOfBound implements Handler {
    private final LoginSessionDao session;
    private final Logger logger = LoggerFactory.getLogger(HashMapSessionDaoImpl.class);

    public LoginOutOfBound(LoginSessionDao session) {
        this.session = session;
    }
    // Tiene que estar autenticada via Basic
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String uuid = ctx.formParam("uuid");
        String token = ctx.formParam("data");
        String challenge= ctx.formParam("challenge");
        if (uuid==null || token == null || challenge == null){
            logger.error(String.format("param error",uuid));
            ctx.status(400);
            return;
        }

        boolean existsUid = session.verify(uuid);
        if (!existsUid){
            // logear
            logger.error(String.format("session does not exist: %s",uuid));
            ctx.status(200);
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> identity = mapper.readValue(token, Map.class);
        identity.put("login_challenge",challenge);
        session.update(uuid,identity);
        ctx.status(200);
    }
}
