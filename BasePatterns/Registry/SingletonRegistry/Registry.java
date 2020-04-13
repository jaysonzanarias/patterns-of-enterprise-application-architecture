package BasePatterns.Registry.SingletonRegistry;

public class Registry {
    private static Registry getInstance() {
        return soleInstance;
    }
    private static Registry soleInstance = new Registry();

    protected PersonFinder personFinder = new PersonFinder();

    public static PersonFinder personFinder() {
        return getInstance().personFinder;
    }

    public static void initialize() {
        soleInstance = new Registry();
    }

    public static void initializeStub() {
        soleInstance = new RegistryStub();
    }
}
