package ObjectRelationalStructuralPatterns.AssociationTableMapping.SingleQueryMultipleEmployees;

import ObjectRelationalStructuralPatterns.AssociationTableMapping.DirectSQL.SkillMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeMapper {
    protected String findStatement() {
        return "SELECT " + COLUMN_LIST +
               " FROM employees employee, skills skill, employeeSkills es" +
               " WHERE employee.ID = es.employeeID AND skill.ID = es.skillID AND employee.ID = ?";
    }

    public static final String COLUMN_LIST =
        " employee.ID, employee.lastname, employee.firstname, " +
        " es.skillID, es.employeeID, skill.ID skillID," +
        SkillMapper.COLUMN_LIST;

    // The abstractFind and load methods on the superclass are the same as in the
    // previous example, so I won’t repeat them here. The employee mapper loads its
    // data differently to take advantage of the multiple data rows.

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException{
        Employee result = (Employee) loadRow(id, rs);
        loadSkillData(result, rs);
        while(rs.next()) {
            Assert.isTrue(rowIsForSameEmployee(id, rs));
            loadSkillData(result, rs);
        }
        return result;
    }

    protected DomainObject loadRow(Long id, ResultSet rs) throws SQLException {
        Employee result = new Employee(id);
        result.setFirstName(rs.getString("firstname"));
        result.setLastName(rs.getString("lastname"));
        return result;
    }

    private boolean rowIsForSameEmployee(Long id, ResultSet rs) throws SQLException {
        return id.equals(new Long(rs.getLong(1)));
    }

    private void loadSkillData(Employee person, ResultSet rs) throws SQLException {
        Long skillID = new Long(rs.getLong("skillID"));
        person.addSkill((Skill) MapperRegistry.skill().loadRow(skillID, rs));
    }

    public List findAll() {
        return findAll(findAllStatement);
    }

    private static final String findAllStatement =
        "SELECT " + COLUMN_LIST +
        " FROM employees employee, skills skill, employeeSkills es" +
        " WHERE employee.ID = es.employeeID AND skill.ID = es.skillID" +
        " ORDER BY employee.lastname";

    protected List findAll(String sql) {
        AssociationTableLoader loader  new AssociationTableLoader(this, new SkillAdder());
        return loader.run(findAllStatement);
    }

    private static class SkillAdder implements AssociationTableLoader.Adder {
        public void add(DomainObject host, ResultSet rs) throws SQLException {
            Employee emp = (Employee) host;
            Long skillId = new Long(rs.getLong("skillId"));
            emp.addSkill((Skill) MapperRegistry.skill().loadRow(skillId, rs));
        }
    }
}
