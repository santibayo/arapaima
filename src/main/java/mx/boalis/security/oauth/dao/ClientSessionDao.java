package mx.boalis.security.oauth.dao;

public interface ClientSessionDao {
    boolean sessionExists(String app, String subject);
}
