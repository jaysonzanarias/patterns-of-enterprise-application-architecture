package ObjectRelationalBehavioralPatterns.UnitOfWork;

public class Album extends DomainObject{
    private String title;

    public static Album create(String name) {
        Album obj = new Album(IdGenerator.nextId(), name);
        obj.markNew();
        return obj;
    }

    public void setTitle(String title) {
        this.title = title;
        markDirty();
    }
}
