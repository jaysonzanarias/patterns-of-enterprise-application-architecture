package ObjectRelationalMetadataMappingPatterns.MetadataMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class Mapper {

    public Object findObject(Long key) {
        if (uow.isLoaded(key)) {
            return uow.getObject(key);
        }
        String sql =
            "SELECT" + dataMap.columnList() + " FROM " + dataMap.getTableName() + " WHERE ID = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        DomainObject result = null;

        try {
            stmt = DB.prepare(sql);
            stmt.setLong(1, key.longValue());
            rs = stmt.executeQuery();
            rs.next();
            result = load(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
        return result;
    }

    private UnitOfWork uow;
    protected DataMap dataMap;

    public DomainObject load(ResultSet rs)
        throws InstantiationException, IllegalAccessException, SQLException {
        Long key = new Long(rs.getLong("ID"));
        if (uow.isLoaded(key)) {
            return uow.getObject(key);
        }
        DomainObject result = (DomainObject) dataMap.getDomainClass().newInstance();
        result.setID(key);
        uow.registerClean(result);
        loadFields(rs, result);
        return result;
    }

    private void loadFields(ResultSet rs, DomainObject result) throws SQLException {
        for (Iterator it = dataMap.getColumns(); it.hasNext(); ) {
            ColumnMap columnMap = (ColumnMap) it.next();
            Object columnValue = rs.getObject(columnMap.getColumnName());
            columnMap.setField(result, columnValue);
        }
    }

    public void update(DomainObject obj) {
        String sql = "UPDATE " + dataMap.getTableName() + dataMap.updateList() + " WHERE ID = ?";
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(sql);
            int argCount = 1;
            for (Iterator it = dataMap.getColumns(); it.hasNext(); ) {
                ColumnMap col = (ColumnMap) it.next();
                stmt.setObject(argCount++, col.getValue(obj));
            }
            stmt.setLong(argCount, obj.getID().longValue());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public Long insert(DomainObject obj) {
        String sql =
            "INSERT INTO " + dataMap.getTableName() + " VALUES (?" + dataMap.insertList() + ")";
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(sql);
            stmt.setObject(1, obj.getID());
            int argCount = 2;
            for (Iterator it = dataMap.getColumns(); it.hasNext(); ) {
                ColumnMap col = (ColumnMap) it.next();
                stmt.setObject(argCount++, col.getValue(obj));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
        return obj.getID();
    }

    public Set findObjectsWhere(String whereClause) {
        String sql =
            "SELECT" + dataMap.columnList() + " FROM " + dataMap.getTableName() + " WHERE " +
            whereClause;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Set result = new HashSet();
        try {
            stmt = DB.prepare(sql);
            rs = stmt.executeQuery();
            result = loadAll(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
        return result;
    }

    public Set loadAll(ResultSet rs)
        throws SQLException, InstantiationException, IllegalAccessException {
        Set result = new HashSet();
        while (rs.next()) {
            DomainObject newObj = (DomainObject) dataMap.getDomainClass().newInstance();
            newObj = load(rs);
            result.add(newObj);
        }
        return result;
    }
}


