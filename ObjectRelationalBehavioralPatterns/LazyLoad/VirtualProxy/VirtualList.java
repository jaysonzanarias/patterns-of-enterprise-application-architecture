package ObjectRelationalBehavioralPatterns.LazyLoad.VirtualProxy;

import java.util.List;

public class VirtualList {
    private List source;
    private VirtualListLoader loader;

    public VirtualList(VirtualListLoader loader) {
        this.loader = loader;
    }

    public VirtualListLoader getLoader() {
        return loader;
    }

    public void setLoader(VirtualListLoader loader) {
        this.loader = loader;
    }

    private List getSource() {
        if(source == null) {
            source = loader.load();
        }
        return source;
    }

    public int size() {
        return getSource().size();
    }

    public boolean isEmpty() {
        return getSource().isEmpty();
    }

    // ... and so on for rest of list methods
}
