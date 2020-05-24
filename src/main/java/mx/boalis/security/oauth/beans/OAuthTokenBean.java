package mx.boalis.security.oauth.beans;

public class OAuthTokenBean {
    private String access_token;
    private String scope;
    private String refresh_token;
    private String token_type;


    public OAuthTokenBean(String access_token, String scope, String refresh_token, String token_type) {
        this.access_token = access_token;
        this.scope = scope;
        this.refresh_token = refresh_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getScope() {
        return scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
