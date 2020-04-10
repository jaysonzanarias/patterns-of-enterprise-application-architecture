package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

public class Address extends DomainObject {
    public static Address create(Customer customer, Version version, String line1, String city,
                                 String state) {
        return new Address(IdGenerator.INSTANCE.nextId(), version, customer, line1, city, state);
    }
}
