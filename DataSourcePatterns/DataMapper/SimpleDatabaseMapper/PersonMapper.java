package DataSourcePatterns.DataMapper.SimpleDatabaseMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonMapper extends AbstractMapper{
    protected String findStatement() {
        return "SELECT " + COLUMNS + " FROM people WHERE id = ?";
    }

    public static final String COLUMNS = " id, lastname, firstname, number_of_dependents ";

    public Person find(Long id) {
        return (Person) abstractFind(id);
    }

    public Person find(long id) {
        return find(new Long(id));
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        return new Person(id, lastNameArg, firstNameArg, numDependentsArg);
    }

    private static String findLastNameStatement = "SELECT " + COLUMNS + " FROM people WHERE UPPER(lastname) " +
            "LIKE UPPER(?) ORDER BY lastname";

    public List findByLastName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findLastNameStatement);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    // ^ Writing a find method this way in each subclass that needs it involves some basic, but repetitive, coding,
    // which I can eliminate by providing a general method. ----------------------- START ------------------------------
    public List findByLastName2(String pattern) {
        return findMany(new FindByLastName(pattern));
    }

    static class FindByLastName implements StatementSource {
        private String lastName;

        public FindByLastName(String lastName) {
            this.lastName = lastName;
        }

        public String sql() {
            return "SELECT " + COLUMNS + " FROM people WHERE UPPER(lastname) like UPPER(?) ORDER BY lastname";
        }

        public Object[] parameters() {
            Object[] result = {lastName};
            return result;
        }
    }
    // Writing a find method this way in each subclass that needs it involves some basic, but repetitive, coding,
    // which I can eliminate by providing a general method. ------------------------ END -------------------------------

    private static final String updateStatementString = "UPDATE people SET lastname = ?, firstname = ?, " +
            "number_of_dependents = ? WHERE id = ?";

    public void update(Person subject) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, subject.getLastName());
            updateStatement.setString(2, subject.getFirstName());
            updateStatement.setInt(3, subject.getNumberOfDependents());
            updateStatement.setInt(4, subject.getID().intValue());
            updateStatement.execute();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(updateStatement);
        }
    }

    protected String insertStatement() {
        return "INSERT INTO people VALUES (?, ?, ?, ?)";
    }

    protected void doInsert(DomainObject abstractSubject, PreparedStatement stmt) throws SQLException {
        Person subject = (Person) abstractSubject;
        stmt.setString(2, subject.getLastName());
        stmt.setString(3, subject.getFirstName());
        stmt.setInt(4, subject.getNumberOfDependents());
    }
}
