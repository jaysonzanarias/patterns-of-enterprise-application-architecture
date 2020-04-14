package BasePatterns.Plugin;

public interface IdGenerator {
    public Long nextId();

    public static final IdGenerator INSTANCE =
        (IdGenerator) PluginFactory.getPlugin(IdGenerator.class);

}
