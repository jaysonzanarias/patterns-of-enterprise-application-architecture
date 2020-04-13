package BasePatterns.Registry.ThreadSafeRegistry;

public class ThreadLocalRegistry {
    private static ThreadLocal instances = new ThreadLocal();

    public static ThreadLocalRegistry getInstance() {
        return (ThreadLocalRegistry) instances.get();
    }

    public static void begin() {
        Assert.isTrue(instances.get() == null);
        instances.set(new ThreadLocalRegistry());
    }

    public static void end() {
        Assert.notNull(getInstance());
        instances.set(null);
    }

    private PersonFinder personFinder = new PersonFinder();
    ;

    public static PersonFinder personFinder() {
        return getInstance().personFinder;
    }
}
