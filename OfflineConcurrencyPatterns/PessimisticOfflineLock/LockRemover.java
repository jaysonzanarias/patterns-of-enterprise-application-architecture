package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public class LockRemover implements HttpSessionBindingListener {
    private String sessionId;

    public LockRemover(String sessionId) {
        this.sessionId = sessionId;
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            beginSystemTransaction();
            ExclusiveReadLockManager.INSTANCE.relaseAllLocks(this.sessionId);
            commitSystemTransaction();
        } catch (Exception e) {
            handleSeriousError(e);
        }
    }
}
