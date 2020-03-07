package ObjectRelationalStructuralPatterns.AssociationTableMapping.DirectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {
    public Employee find(long key) {
        return find(new Long(key));
    }

    public Employee find(Long key) {
        return (Employee) abstractFind(key);
    }

    protected String findStatement() {
        return "SELECT " + COLUMN_LIST + " FROM employees WHERE ID = ?";
    }

    public static final String COLUMN_LIST = " ID, lastname, firstname ";

    public DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        Employee result = new Employee(id);
        result.setFirstName(rs.getString("firstname"));
        result.setLastName(rs.getString("lastname"));
        result.setSkills(loadSkills(id));
        return result;
    }

    protected List loadSkills(Long employeeID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            List result = new ArrayList();
            stmt = DB.prepare(findSkillsStatement);
            stmt.setObject(1, employeeID);
            rs = stmt.executeQuery();
            while(rs.next()) {
                Long skillId = new Long(rs.getLong(1));
                result.add((Skill) MapperRegistry.skill().loadRow(skillId, rs));
            }
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    private static final String findSkillsStatement =
        "SELECT skill.ID, " + SkillMapper.COLUMN_LIST +
        " FROM skills skill, employeeSkills es " +
        " WHERE es.employeeID = ? AND skill.ID = es.skillID";

    public List findAll() {
        return findAll(findAllStatement);
    }

    private static final String findAllStatement =
        "SELECT " + COLUMN_LIST +
        " FROM employees employee" +
        " ORDER BY employee.lastname";
}
