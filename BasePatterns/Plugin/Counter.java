package BasePatterns.Plugin;

public class Counter implements IdGenerator {
    private long count = 0;

    public synchronized Long nextId() {
        return new Long(count++);
    }
}
