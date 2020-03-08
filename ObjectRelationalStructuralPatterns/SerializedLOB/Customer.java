package ObjectRelationalStructuralPatterns.SerializedLOB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer {
    private String name;
    private List departments = new ArrayList();

    public Long insert() {
        PreparedStatement insertStatement = null;

        try {
            insertStatement = DB.prepare(insertStatementString);
            setID(findNextDatabaseId());
            insertStatement.setInt(1, getID().intValue());
            insertStatement.setString(2, name);
            insertStatement.setString(3, XmlStringer.write(departmentsToXmlElement()));
            insertStatement.execute();
            Registry.addCustomer(this);
            return getID();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(insertStatement);
        }
    }

    public Element departmentsToXmlElement() {
        Element root = new Element("departmentList");
        Iterator i = departments.iterator();
        while(i.hasNext()) {
            Department dep = (Department) i.next();
            root.addContent(dep.toXmlElement());
        }
    }

    public static Customer load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong("id"));
        Customer result = (Customer) Regisry.getCustomer(id);

        if(result != null) {
            return result;
        }

        String name = rs.getString("name");
        String departmentLob = rs.getString("departments");
        result = new Customer(name);
        result.readDepartments(XmlStringer.read(departmentLob));
        return result;
    }

    void readDepartments(Element source) {
        List result = new ArrayList();
        Iterator it = source.getChildren("department").iterator();
        while(it.hasNext()) {
            addDepartment(Department.readXml((Element) it.next()));
        }
    }
}
