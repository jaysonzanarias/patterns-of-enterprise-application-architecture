package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedPessimisticOfflineLock;

public class LoadCustomerCommand {
    ...
    try {
        Customer customer = (Customer) MapperRegistry.getMapper(Customer.class).find(id);
        ExclusiveReadLockManager.INSTANCE.acquireLock
            (customer.getId(), AppSessionManager.getSession().getId());
        customer.getVersion().increment();
        TransactionManager.INSTANCE.commit();
    } catch (Exception e) {
        TransactionManager.INSTANCE.rollback();
        throw e;
    }
    ...
}
