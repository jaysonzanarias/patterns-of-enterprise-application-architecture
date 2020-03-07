package ObjectRelationalStructuralPatterns.ForeignKeyMapping.SingleValuedReference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMapper {
    abstract protected String findStatement();
    protected DomainObject abstractFind(Long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);
        if(result != null) {
            return result;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(findStatement());
            stmt.setLong(1, id.longValue());
            rs = stmt.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            cleanUp(stmt, rs);
        }
    }

    protected DomainObject load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        if(loadedMap.containsKey(id)) {
            return (DomainObject) loadedmap.get(id);
        }

        DomainObject result = doLoad(id, rs);
        doRegister(id, result);
        return result;
    }

    protected void doRegister(Long id, DomainObject result) {
        Assert.isFalse(loadedMap.containsKey(id));
        loadedMap.put(id, result);
    }

    abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;
    abstract public void update(DomainObject arg);
}
