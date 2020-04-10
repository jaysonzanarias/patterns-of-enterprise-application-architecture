package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public class AppSession {
    private String user;
    private String id;
    private IdentityMap imap;

    public AppSession(String user, String id, IdentityMap imap) {
        this.user = user;
        this.imap = imap;
        this.id = id;
    }
}
