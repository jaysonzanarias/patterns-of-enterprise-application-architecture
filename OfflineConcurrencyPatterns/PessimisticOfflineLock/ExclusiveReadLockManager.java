package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public interface ExclusiveReadLockManager {
    public static final ExclusiveReadLockManager INSTANCE =
        (ExclusiveReadLockManager) Plugins.getPlugin(ExclusiveReadLockManager.class);
    public void acquireLock(Long lockable, String owner) throws ConcurrencyException;
    public void releaseLock(Long lockable, String owner);
    public void relaseAllLocks(String owner);
}
