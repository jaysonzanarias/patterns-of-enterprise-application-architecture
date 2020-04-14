package BasePatterns.Plugin;

public class Customer extends DomainObject {
    private Customer(String name, Long id) {
        super(id);
        this.name = name;
    }
    public Customer create(String name) {
        Long newObjId = IdGenerator.INSTANCE.nextId();
        Customer obj = new Customer(name, newObjId);
        obj.markNew();
        return obj;
    }
}
