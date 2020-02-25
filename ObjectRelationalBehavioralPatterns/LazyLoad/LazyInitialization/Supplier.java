package ObjectRelationalBehavioralPatterns.LazyLoad.LazyInitialization;

import java.util.List;

public class Supplier {
    public List getProducts() {
        if(products == null) {
            products = Product.findForSupplier(getID());
        }
        return products;
    }
}
