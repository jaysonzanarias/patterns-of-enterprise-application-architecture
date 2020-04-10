package OfflineConcurrencyPatterns.OptimisticOfflineLock;

public class DomainObject {
    private Timestamp modified;
    private String modifiedBy;
    private int version;
}
