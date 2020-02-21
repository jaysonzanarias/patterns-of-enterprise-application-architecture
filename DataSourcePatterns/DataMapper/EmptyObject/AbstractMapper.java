package DataSourcePatterns.DataMapper.EmptyObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AbstractMapper {
    protected Map loadedMap = new HashMap();

    protected DomainObject load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        if(loadedMap.containsKey(id)) {
            return (DomainObject) loadedMap.get(id);
        }

        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;


    // Empty Object way --- START ---
    protected DomainObjectEL load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        if(loadedMap.containsKey(id)) {
            return (DomainObject) loadedMap.get(id);
        }

        DomainObjectEL result = createDomainObject();
        result.setID(id);
        loadedMap.put(id, result);
        doLoad(result, rs);
        return result;
    }

    abstract protected DomainObjectEL createDomainObject();
    abstract protected void doLoad(DomainObjectEL obj, ResultSet rs) throws SQLException;
    // Empty Object way --- END ---
}
