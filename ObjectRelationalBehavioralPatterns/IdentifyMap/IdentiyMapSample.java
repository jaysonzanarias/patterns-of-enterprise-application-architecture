package ObjectRelationalBehavioralPatterns.IdentifyMap;

import java.util.HashMap;
import java.util.Map;

public class IdentiyMapSample {
    private Map people = new HashMap();

    public static void addPerson(Person arg) {
        soleInstance.people.put(arg.getID(), arg);
    }
    public static Person getPerson(Long key) {
        return (Person) soleInstance.people.get(key);
    }
    public static Person getPerson(long key) {
        return getPerson(new Long(key));
    }
}
