package ObjectRelationalBehavioralPatterns.LazyLoad.Ghost.StackOverflow;

public class Ghost {
    private final int count;
    private boolean initialized = false;
    private Complicated complicated = null;

    public Ghost(int count) {
        this.count = count;
    }

    public Complicated getComplicated(String extraValue) {
        // could also check (here and below) this.complicated == null
        // in that case doExpensiveOperation should not return null, and there shouldn't be other fields to initialize
        if (!initialized) {
            synchronized (this) { // if you want concurrent access
                if (!initialized) {
                    complicated = doExpensiveOperation(extraValue);
                    initialized = true;
                }
            }
        }

        return complicated;
    }

    private Complicated doExpensiveLoad(String extraValue) {
        // do expensive things with this.count and extraValue
        // think database/network calls, or even hashcode calculations for big objects
    }
}
