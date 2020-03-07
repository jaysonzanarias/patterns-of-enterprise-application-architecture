package ObjectRelationalStructuralPatterns.AssociationTableMapping.DirectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapper {
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
            DB.cleanUp(stmt, rs);
        }
    }

    abstract protected String findStatement();
    protected Map loadedMap = new HashMap();

    protected DomainObject load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        return load(id, rs);
    }

    public DomainObject load(Long id, ResultSet rs) throws SQLException {
        if (hasLoaded(id)) {
            return (DomainObject) loadedMap.get(id);
        }

        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;

    protected DomainObject loadRow(Long id, ResultSet rs) throws SQLException {
        return load(id, rs);
    }

    protected List findAll(String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            List result = new ArrayList();
            stmt = DB.prepare(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(load(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
