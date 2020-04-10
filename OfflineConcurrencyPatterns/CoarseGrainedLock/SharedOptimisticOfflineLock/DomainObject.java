package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

import java.sql.Timestamp;

public class DomainObject {
    private Long id;
    private Timestamp modified;
    private String modifiedBy;
    private Version version;

    public void setSystemFields(Version version, Timestamp modified, String modifiedBy) {
        this.version = version;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }
}
