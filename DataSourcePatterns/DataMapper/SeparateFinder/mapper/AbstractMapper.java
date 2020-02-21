package DataSourcePatterns.DataMapper.SeparateFinder.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper {
    abstract protected String findStatement();

    protected Map loadedMap = new HashMap();

    protected DomainObject abstractFind(Long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);
        if (result != null) {
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
        Long id = new Long(rs.getLong("id"));
        if(loadedMap.containsKey(id)) {
            return (DomainObject) loadedMap.get(id);
        }

        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    abstract protected DOmainObject doLoad(Long id, ResultSet rs) throws SQLException;
}
