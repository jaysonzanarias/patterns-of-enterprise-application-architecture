package BasePatterns.Registry.SingletonRegistry;

public class RegistryStub extends Registry {
    public RegistryStub() {
        personFinder = new PersonFinderStub();
    }
}
