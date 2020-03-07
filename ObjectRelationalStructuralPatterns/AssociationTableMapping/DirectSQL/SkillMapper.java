package ObjectRelationalStructuralPatterns.AssociationTableMapping.DirectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillMapper {
    public static final String COLUMN_LIST = " skill.name skillName ";

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        Skill result = new Skill(id);
        result.setName(rs.getString("skillName"));
        return result;
    }
}
