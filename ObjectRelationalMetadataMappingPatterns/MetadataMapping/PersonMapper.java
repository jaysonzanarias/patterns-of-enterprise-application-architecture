package ObjectRelationalMetadataMappingPatterns.MetadataMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonMapper {
    protected void loadDataMap() {
        dataMap = new DataMap(Person.class, "people");
        dataMap.addColumn("lastname", "varchar", "lastName");
        dataMap.addColumn("firstname", "varchar", "firstName");
        dataMap.addColumn("number_of_dependents", "int", "numberOfDependents");
    }

    public Person find(Long key) {
        return (Person) findObject(key);
    }

    public Set findLastNamesLike(String pattern) {
        String sql = "SELECT" + dataMap.columnList() + " FROM " + dataMap.getTableName() +
                     " WHERE UPPER(lastName) like UPPER(?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(sql);
            stmt.setString(1, pattern);
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
