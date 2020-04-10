package OfflineConcurrencyPatterns.CoarseGrainedLock.RootOptimisticOfflineLock;

import java.util.Iterator;

public class UnitOfWork {
    public void commit() throws SQLException {
        for (Iterator iterator = _modifiedObjects.iterator(); iterator.hasNext(); ) {
            DomainObject object = (DomainObject) iterator.next();
            for (DomainObject owner = object; owner != null; owner = owner.getParent()) {
                owner.getVersion().increment();
            }
        }
        for (Iterator iterator = _modifiedObjects.iterator(); iterator.hasNext(); ) {
            DomainObject object = (DomainObject) iterator.next();
            Mapper mapper = MapperRegistry.getMapper(object.getClass());
            mapper.update(object);
        }
    }
}
