package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

import java.util.ArrayList;

public class Customer extends DomainObject {
    public static Customer create(String name) {
        return new Customer(IdGenerator.INSTANCE.nextId(), Version.create(), name, new ArrayList());
    }

    public Address addAddress(String line1, String city, String state) {
        Address address = Address.create(this, getVersion(), line1, city, state);
        addresses.add(address);
        return address;
    }
}
