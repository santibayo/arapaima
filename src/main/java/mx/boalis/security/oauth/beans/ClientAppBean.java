package mx.boalis.security.oauth.beans;

public class ClientAppBean {
    String name;
    String clientId;
    String clientSecret;

    public ClientAppBean(String name, String clientId, String clientSecret) {
        this.name = name;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
