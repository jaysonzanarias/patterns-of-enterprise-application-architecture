package ObjectRelationalBehavioralPatterns.UnitOfWork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnitOfWork {
    private List newObjects = new ArrayList();
    private List dirtyObjects = new ArrayList();
    private List removedObjects = new ArrayList();

    public void registerNew(DomainObject obj) {
        Assert.notNull("id not null", obj.getId());
        Assert.isTrue("object not dirty", !dirtyObjects.contains(obj));
        Assert.isTrue("object not removed", !removedObjects.contains(obj));
        Assert.isTrue("object not already registered new", !newObjects.contains(obj));
        newObjects.add(obj);
    }

    public void registerDirty(DomainObject obj) {
        Assert.notNull("id not null", obj.getId());
        Assert.isTrue("object not removed", !removedObjects.contains(obj));
        if(!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
            dirtyObjects.add(obj);
        }
    }

    public void registerRemoved(DomainObject obj) {
        Assert.notNull("id not null", obj.getId());
        if(newObjects.remove(obj))
            return;

        if (!removedObjects.contains(obj)) {
            removedObjects.add(obj);
        }
    }

    public void registerClean(DomainObject obj) {
        Assert.notNull("id not null", obj.getId());
    }

    public void commit() {
        insertNew();
        updateDirty();
        deleteRemoved();
    }

    private void insertNew() {
        for (Iterator objects = newObjects.iterator(); objects.hasNext()) {
            DomainObject obj = (DomainObject) objects.next();
            MapperRegistry.getMapper(obj.getClass()).insert(obj);
        }
    }

    private void updateDirty() {
        for (Iterator objects = dirtyObjects.iterator(); objects.hasNext()) {
            DomainObject obj = (DomainObject) objects.next();
            MapperRegistry.getMapper(obj.getClass()).update(obj);
        }
    }

    private void deleteRemoved() {
        for (Iterator objects = removedObjects.iterator(); objects.hasNext()) {
            DomainObject obj = (DomainObject) objects.next();
            MapperRegistry.getMapper(obj.getClass()).delete(obj);
        }
    }

    private static ThreadLocal current = new ThreadLocal();

    public static void newCurrent() {
        setCurrent(new UnitOfWork());
    }

    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }
}
