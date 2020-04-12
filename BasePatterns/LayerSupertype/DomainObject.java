package BasePatterns.LayerSupertype;

public class DomainObject {
    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        Assert.notNull("Cannot set a null ID", ID);
        this.ID = ID;
    }

    public DomainObject(Long ID) {
        this.ID = ID;
    }

    public DomainObject() {
    }
}
