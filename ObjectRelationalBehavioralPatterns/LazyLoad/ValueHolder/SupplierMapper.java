package ObjectRelationalBehavioralPatterns.LazyLoad.ValueHolder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierMapper {
    public static class ProductLoader implements ValueLoader {
        private Long id;
        public ProductLoader(Long id) {
            this.id = id;
        }

        public Object load() {
            return ProductMapper.create().findForSupplier(id);
        }
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String nameArg = rs.getString(2);
        SupplierVH result = new SupplierVH(id, nameArg);
        result.setProducts(new ValueHolder(new ProductLoader(id)));
        return result;
    }
}
