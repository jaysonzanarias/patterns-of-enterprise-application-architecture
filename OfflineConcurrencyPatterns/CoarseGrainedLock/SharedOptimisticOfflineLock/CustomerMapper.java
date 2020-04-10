package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

import java.util.Iterator;

public class CustomerMapper extends AbstractMapper {

    public void delete(DomainObject object) {
        Customer cust = (Customer) object;
        for (Iterator iterator = cust.getAddresses().iterator(); iterator.hasNext(); ) {
            Address add = (Address) iterator.next();
            MapperRegistry.getMapper(Address.class).delete(add);
        }
        super.delete(object);
        cust.getVersion().delete();
    }
}
