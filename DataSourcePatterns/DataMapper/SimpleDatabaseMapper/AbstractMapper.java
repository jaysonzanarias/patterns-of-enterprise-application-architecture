package DataSourcePatterns.DataMapper.SimpleDatabaseMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class AbstractMapper {
    protected Map loadedMap = new HashMap();

    abstract protected String findStatement();

    protected DomainObject abstractFind(Long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);
        if (result != null) return result;
        PreparedStatement findStatement = null;
        try {
            findStatement = DB.prepare(findStatement());
            findStatement.setLong(1, id.longValue());
            ResultSet rs = findStatement.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(findStatement);
        }
    }

    protected DomainObject load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        if (loadedMap.containsKey(id)) return (DomainObject) loadedMap.get(id);
        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;

    protected List loadAll(ResultSet rs) throws SQLException {
        List result = new ArrayList();
        while (rs.next())
            result.add(load(rs));
        return result;
    }

    // ^ Writing a find method this way in each subclass that needs it involves some basic, but repetitive, coding,
    // which I can eliminate by providing a general method. ----------------------- START ------------------------------
    public List findMany(StatementSource source) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(source.sql());
            for (int i = 0; i < source.parameters().length; i++) {
                stmt.setObject(i + 1, source.parameters()[i]);
            }
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
    // Writing a find method this way in each subclass that needs it involves some basic, but repetitive, coding,
    // which I can eliminate by providing a general method. ------------------------ END -------------------------------

    public Long insert(DomainObject subject) {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatement());
            subject.setID(findNextDatabaseId());
            insertStatement.setInt(1, subject.getID().intValue());
            doInsert(subject, insertStatement);
            insertStatement.execute();
            loadedMap.put(subject.getID(), subject);
            return subject.getID();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(insertStatement);
        }
    }

    abstract protected String insertStatement();

    abstract protected void doInsert(DomainObject subject, PreparedStatement insertStatement)
            throws SQLException;
}
