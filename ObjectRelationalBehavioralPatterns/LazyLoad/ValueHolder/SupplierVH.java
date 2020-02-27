package ObjectRelationalBehavioralPatterns.LazyLoad.ValueHolder;

import java.util.List;

public class SupplierVH {
    private ValueHolder products;
    public List getProducts() {
        return (List) products.getValue();
    }
}
