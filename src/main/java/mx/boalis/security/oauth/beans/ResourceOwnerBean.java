package mx.boalis.security.oauth.beans;

import java.util.Map;

public class ResourceOwnerBean {
    String code;
    String state;
    String redirectUrl;
    String clientId;
    String responseType;
    Map<String,String> accessTokenInfo;

    public Map<String, String> getAccessTokenInfo() {
        return accessTokenInfo;
    }

    public void setAccessTokenInfo(Map<String, String> accessTokenInfo) {
        this.accessTokenInfo = accessTokenInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
