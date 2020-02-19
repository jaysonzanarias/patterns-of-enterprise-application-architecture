package DataSourcePatterns.ActiveRecords;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    private int id;
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    public Person(int id, String lastName, String firstName, int numberOfDependents) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberOfDependents = numberOfDependents;
    }

    public Person(Long id, String lastName, String firstName, int numberOfDependents) {
        new Person(id.longValue(), lastName, firstName, numberOfDependents);
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    private final static String findStatementString = "SELECT id, lastname, firstname, number_of_dependents " +
            "FROM people WHERE id = ?";

    public static Person find(Long id) {
        Person result = (Person) Registry.getPerson(id);
        if(result != null) {
            return result;
        }

        PreparedStatement findStament = null;
        ResultSet rs = null;

        try {
            findStament = DB.prepare(findStatementString);
            findStament.setLong(1, id.longValue());
            rs = findStament.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(findStament, rs);
        }
    }

    public static Person find(long id) {
        return find(new Long(id));
    }

    public static Person load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        Person result = (Person) Registry.getPerson(id);
        if(result != null) {
            return result;
        }

        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numbDependentsArg = rs.getInt(4);
        result = new Person(id, lastNameArg, firstNameArg, numbDependentsArg);
        Registry.addPerson(result);
        return result;
    }

    private final static String updateStatementString = "UPDATE people SET lastname = ?, firstname = ?, " +
            "number_of_dependents = ? WHERE id = ?";

    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setInt(4, getID().intValue());
            updateStatement.execute();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(updateStatement);
        }
    }

    private final static String insertStatementString = "INSERT INTO people VALUES(?, ?, ?, ?)";

    public Long insert() {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString);
            setID(findNextDatabaseId());
            insertStatement.setInt(1, getID().intValue());
            insertStatement.setString(2, firstName);
            insertStatement.setString(3, lastName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.execute();
            Registry.addPerson(this);
            return getID();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(insertStatement);
        }
    }

    public Money getExemption() {
        Money baseExemption = Money.dollars(1500);
        Money dependentExemption = Money.dollars(750);
        return baseExemption.add(dependentExemption.multiply(this.getNumberOfDependents()));
    }
}
