package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

public class AbstractMapper {
    public void insert(DomainObject object) {
        object.getVersion().insert();
    }

    public void update(DomainObject object) {
        object.getVersion().increment();
    }

    public void delete(DomainObject object) {
        object.getVersion().increment();
    }
}
