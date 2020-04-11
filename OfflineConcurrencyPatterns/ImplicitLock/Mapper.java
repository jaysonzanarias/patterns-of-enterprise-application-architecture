package OfflineConcurrencyPatterns.ImplicitLock;

public interface Mapper {
    public DomainObject find(Long id);
    public void insert(DomainObject obj);
    public void update(DomainObject obj);
    public void delete(DomainObject obj);
}
