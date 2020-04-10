package OfflineConcurrencyPatterns.OptimisticOfflineLock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnitOfWork {
    private List reads = new ArrayList();

    public void registerRead(DomainObject object) {
        reads.add(object);
    }

    public void commit() {
        try {
            checkConsistentReads();
            insertNew();
            deleteRemoved();
            updateDirty();
        } catch (ConcurrencyException e) {
            rollbackSystemTransaction();
            throw e;
        }
    }

    public void checkConsistentReads() {
        for (Iterator iterator = reads.iterator(); iterator.hasNext(); ) {
            DomainObject dependent = (DomainObject) iterator.next();
            dependent.getVersion().increment();
        }
    }
}
