package OfflineConcurrencyPatterns.ImplicitLock;

public class LockingMapper implements Mapper {
    private Mapper impl;

    public LockingMapper(Mapper impl) {
        this.impl = impl;
    }

    public DomainObject find(Long id) {
        ExclusiveReadLockManager.INSTANCE.acquireLock(id, AppSessionManager.getSession().getId());
        return impl.find(id);
    }

    public void insert(DomainObject obj) {
        impl.insert(obj);
    }

    public void update(DomainObject obj) {
        impl.update(obj);
    }

    public void delete(DomainObject obj) {
        impl.delete(obj);
    }
}
