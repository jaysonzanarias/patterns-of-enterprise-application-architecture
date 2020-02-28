package ObjectRelationalStructuralPatterns.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LineItemMapper extends AbstractMapper {
    public LineItem find(long orderID, long seq) {
        Key key = new Key(new Long(orderID), new Long(seq));
        return (LineItem) abstractFind(key);
    }

    public LineItem find(Key key) {
        return (LineItem) abstractFind(key);
    }

    protected String findStatementString() {
        return "SELECT orderID, seq, amount, product " + "FROM line_items " +
               "WHERE (orderID = ?) AND (seq = ?)";
    }

    // hook methods overridden for the composite key
    protected void loadFindStatement(Key key, PreparedStatement finder) throws SQLException {
        finder.setLong(1, orderID(key));
        finder.setLong(2, sequenceNumer(key));
    }

    // helps to extract appropriate values from line item's key
    private static long orderID(Key key) {
        return key.longValue(0);
    }

    private static long sequenceNumer(Key key) {
        return key.longValue(1);
    }

    protected DomainObjectWithKey doLoad(Key key, ResultSet rs) throws SQLException {
        Order theOrder = MapperRegistry.order().find(orderID(key));
        return doLoad(key, rs, theOrder);
    }

    protected DomainObjectWithKey doLoad(Key key, ResultSet rs, Order order) throws SQLException {
        LineItem result;
        int amount = rs.getInt("amount");
        String product = rs.getString("product");
        result = new LineItem(key, amount, product);
        order.addLineItem(result);//links to the order
        return result;
    }

    //overrides the default case
    protected Key createKey(ResultSet rs) throws SQLException {
        Key key = new Key(new Long(rs.getLong("orderID")), new Long(rs.getLong("seq")));
        return key;
    }

    public void loadAllLineItemsFor(Order arg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findForOrderString);
            stmt.setLong(1, arg.getKey().longValue());
            rs = stmt.executeQuery();
            while (rs.next()) {
                load(rs, arg);
            }
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    private final static String findForOrderString =
        "SELECT orderID, seq, amount, product " + "FROM line_items " + "WHERE orderID = ?";

    protected DomainObjectWithKey load(ResultSet rs, Order order) throws SQLException {
        Key key = createKey(rs);
        if (loadedMap.containsKey(key)) {
            return (DomainObjectWithKey) loadedMap.get(key);
        }
        DomainObjectWithKey result = doLoad(key, rs, order);
        loadedMap.put(key, result);
        return result;
    }

    protected String insertStatementString() {
        return "INSERT INTO line_items VALUES (?, ?, ?, ?)";
    }

    protected void insertKey(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        stmt.setLong(1, orderID(subject.getKey()));
        stmt.setLong(2, sequenceNumber(subject.getKey()));
    }

    protected void insertData(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        LineItem item = (LineItem) subject;
        stmt.setInt(3, item.getAmount());
        stmt.setString(4, item.getProduct());
    }

    public Key insert(DomainObjectWithKey subject) {
        throw new UnsupportedOperationException("Must supply an order when inserting a line item");
    }

    public Key insert(LineItem item, Order order) {
        try {
            Key key = new Key(order.getKey().value(), getNextSequenceNumber(order));
            return performInsert(item, key);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
    }

    private Long getNextSequenceNumber(Order order) {
        loadAllLineItemsFor(order);
        Iterator it = order.getItems().iterator();
        LineItem candidate = (LineItem) it.next();
        while (it.hasNext()) {
            LineItem thisItem = (LineItem) it.next();
            if (thisItem.getKey() == null) {
                continue;
            }
            if (sequenceNumber(thisItem) > sequenceNumber(candidate)) {
                candidate = thisItem;
            }
        }
        return new Long(sequenceNumber(candidate) + 1);
    }

    private static long sequenceNumber(LineItem li) {
        return sequenceNumber(li.getKey());
    }

    //comparator doesn't work well here due to unsaved null keys
    protected String keyTableRow() {
        throw new UnsupportedOperationException();
    }

    protected String updateStatementString() {
        return "UPDATE line_items " + " SET amount = ?, product = ? " +
               " WHERE orderId = ? AND seq = ?";
    }

    protected void loadUpdateStatement(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        stmt.setLong(3, orderID(subject.getKey()));
        stmt.setLong(4, sequenceNumber(subject.getKey()));
        LineItem li = (LineItem) subject;
        stmt.setInt(1, li.getAmount());
        stmt.setString(2, li.getProduct());
    }

    protected String deleteStatementString() {
        return "DELETE FROM line_items WHERE orderid = ? AND seq = ?";
    }

    protected void loadDeleteStatement(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        stmt.setLong(1, orderID(subject.getKey()));
        stmt.setLong(2, sequenceNumber(subject.getKey()));
    }
}
