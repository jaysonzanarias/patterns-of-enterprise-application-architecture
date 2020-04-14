package BasePatterns.Plugin;

public class OracleIdGenerator implements IdGenerator{
    public OracleIdGenerator() {
        this.sequence = Environment.getProperty("id.sequence");
        this.datasource = Environment.getProperty("id.source");
    }
}
