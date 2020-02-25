package ObjectRelationalBehavioralPatterns.LazyLoad.VirtualProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierMapper {
    public static class ProductLoader implements VirtualListLoader {
        private Long id;
        public ProductLoader(Long id) {
            this.id = id;
        }

        public List load() {
            return ProductMapper.create().findForSupplier(id);
        }
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String nameArg = rs.getString(2);
        SupplierVL result = new SupplierVL(id, nameArg);
        result.setProducts(new VirtualList(new ProductLoader(id)));
        return result;
    }
}
