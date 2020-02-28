package ObjectRelationalStructuralPatterns.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper {
    abstract protected String findStatementString();

    protected Map loadedMap = new HashMap();

    public DomainObjectWithKey abstractFind(Key key) {
        DomainObjectWithKey result = (DomainObjectWithKey) loadedMap.get(key);
        if (result != null) {
            return result;
        }

        ResultSet rs = null;
        PreparedStatement findStatement = null;

        try {
            findStatement = DB.prepare(findStatementString());
            loadFindStatement(key, findStatement);
            rs = findStatement.executeQuery();
            rs.next();

            if (rs.isAfterLast()) {
                return null;
            }

            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(findStatement, rs);
        }
    }

    // hook method for keys that aren't simple integral
    protected void loadFindStatement(Key key, PreparedStatement finder) throws SQLException {
        finder.setLong(1, key.longValue());
    }

    protected DomainObjectWithKey load(ResultSet rs) throws SQLException {
        Key key = createKey(rs);
        if (loadedMap.containsKey(key)) {
            return (DomainObjectWithKey) loadedMap.get(key);
        }

        DomainObjectWithKey result = doLoad(key, rs);
        loadedMap.put(key, result);
        return result;
    }

    abstract protected DomainObjectWithKey doLoad(Key id, ResultSet rs) throws SQLException;

    // hook method for keys that aren't simple integral
    protected Key createKey(ResultSet rs) throws SQLException {
        return new Key(rs.getLong(1));
    }

    public Key insert(DomainObjectWithKey subject) {
        try {
            return performInsert(subject, findNextDatabaseKeyObject());
        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
    }

    protected Key performInsert(DomainObjectWithKey subject, Key key) throws SQLException {
        subject.setKey(key);
        PreparedStatement stmt = DB.prepare(insertStatementString());
        insertKey(subject, stmt);
        insertData(subject, stmt);
        stmt.execute();
        loadedMap.put(subject.getKey(), subject);
        return subject.getKey();
    }

    abstract protected String insertStatementString();

    protected void insertKey(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        stmt.setLong(1, subject.getKey().longValue());
    }

    abstract protected void insertData(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException;

    public void update(DomainObjectWithKey subject) {
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(updateStatementString());
            loadUpdateStatement(subject, stmt);
            stmt.execute();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    abstract protected String updateStatementString();
    abstract protected void loadUpdateStatement(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException;

    public void delete(DomainObjectWithKey subject) {
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(deleteStatementString());
            loadDeleteStatement(subject, stmt);
            stmt.execute();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    abstract protected String deleteStatementString();

    protected void loadDeleteStatement(DomainObjectWithKey subject, PreparedStatement stmt)
        throws SQLException {
        stmt.setLong(1, subject.getKey().longValue());
    }
}
