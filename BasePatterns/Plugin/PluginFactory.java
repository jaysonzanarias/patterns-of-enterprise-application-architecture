package BasePatterns.Plugin;

public class PluginFactory {
    private static Properties props = new Properties();

    static {
        try {
            String propsFile = System.getProperty("plugins");
            props.load(new FileInputStream(propsFile));
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Object getPlugin(Class iface) {
        String implName = props.getProperty(iface.getName());
        if (implName == null) {
            throw new RuntimeException("implementation not specified for " + iface.getName() +
                                       " in PluginFactory propeties.");
        }
        try {
            return Class.forName(implName).newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(
                "factory unable to construct instance of " + iface.getName());
        }
    }
}
