package DataSourcePatterns.RowDataGateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonFinder {
    private final static String findStatementString = "SELECT id, lastname, firstname, number_of_dependents " +
            "FROM people WHERE id = ?";

    public PersonGateway find(Long id) {
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if(result != null) {
            return result;
        }

        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = DB.prepare(findStatementString);
            findStatement.setLong(1, id.longValue());
            rs = findStatement.executeQuery();
            rs.next();
            result = PersonGateway.load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally { DB.cleanUp(findStatement, rs);}
    }

    public PersonGateway find(long id) {
        return find(new Long(id));
    }

    private static final String findResponsibleStatement = "SELECT id, lastname, firstname, number_of_dependents " +
            "FROM people WHERE number_of_dependents > 0";

    public List findResponsibles() {
        List result = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findResponsibleStatement);
            rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(PersonGateway.load(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally { DB.cleanUp(stmt, rs); }
    }
}
