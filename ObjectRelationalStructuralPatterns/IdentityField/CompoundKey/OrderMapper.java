package ObjectRelationalStructuralPatterns.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper extends AbstractMapper {
    public Order find(Key key) {
        return (Order) abstractFind(key);
    }

    public Order find(Long id) {
        return find(new Key(id));
    }

    protected String findStatementString() {
        return "SELECT id, customer FROM orders WHERE id = ?";
    }

    protected DomainObjectWithKey doLoad(Key key, ResultSet rs) throws SQLException {
        String customer = rs.getString("customer");
        Order result = new Order(key, customer);
        MapperRegistry.lineItem().loadAllLineItemsFor(result);
        return result;
    }

    protected String insertStatementString() {
        return "INSERT INTO orders VALUES(?,?)";
    }

    protected void insertData(DomainObjectWithKey abstractSubject, PreparedStatement stmt) {
        try {
            Order subject = (Order) abstractSubject;
            stmt.setString(2, subject.getCustomer());
        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
    }

    protected void loadUpdateStatement(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        Order order = (Order) subject;
        stmt.setString(1, order.getCustomer());
        stmt.setLong(2, order.getKey().longValue());
    }

    protected String updateStatementString() {
        return "UPDATE orders SET customer = ? WHERE id = ?";
    }

    protected String deleteStatementString() {
        return "DELETE FROM orders WHERE id = ?";
    }
}
