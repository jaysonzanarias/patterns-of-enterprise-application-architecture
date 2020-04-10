package OfflineConcurrencyPatterns.CoarseGrainedLock.RootOptimisticOfflineLock;

public class DomainObject {
    private Long id;
    private DomainObject parent;

    public DomainObject(Long id, DomainObject parent) {
        this.id = id;
        this.parent = parent;
    }
}
