package DataSourcePatterns.RowDataGateway;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonGateway {
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    private static final String updateStatementString = "UPDATE people " +
            "SET lastname = ?, firstname = ?, number_of_dependents = ? " +
            "WHERE id = ?";

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
            throw new ApplicaitonException(e);
        } finally { DB.cleanUp(updateStatement);}
    }

    private static final String insertStatementString =  "INSERT INTO people VALUES(?,?,?,?)";

    public Long insert() {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString);
            setID(findNextDatabaseId());
            insertStatement.setInt(1, getID().intValue());
            insertStatement.setString(2, lastName);
            insertStatement.setString(3, firstName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.execute();
            Registry.addPerson(this);
            return getID();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally { DB.cleanUp(insertStatement);}
    }

    public static PersonGateway load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if(result != null) {
            return result;
        }

        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentArg = rs.getInt(4);

        result = new PersonGateway(id, lastNameArg, firstNameArg, numDependentArg);
        Registry.addPerson(result);

        return result;
    }
}
