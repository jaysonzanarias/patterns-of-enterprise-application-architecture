package ObjectRelationalBehavioralPatterns.LazyLoad.VirtualProxy;

import java.util.List;

public class SupplierVL {
    private long id;
    private String name;
    private List products;

    public SupplierVL(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setProducts(VirtualList virtualList) {
        this.products = virtualList.getLoader().load();
    }
}
