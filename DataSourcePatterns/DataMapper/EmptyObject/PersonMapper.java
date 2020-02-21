package DataSourcePatterns.DataMapper.EmptyObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper {
    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        return new Person(id, lastNameArg, firstNameArg, numDependentsArg);
    }

	// Empty Object way --- START ---
    protected DomainObjectEL createDomainObject() {
    	return new Person();
    }

    protected void doLoad(DomainObjectEL obj, ResultSet rs) throws SQLException {
    	Person person = (Person) obj;
    	person.dbLoadLastName(rs.getString(2));
    	person.setFirstName(rs.getString(3));
    	person.setNumberOfDependents(rs.getInt(4));
    }
    // Empty Object way --- END ---
}
