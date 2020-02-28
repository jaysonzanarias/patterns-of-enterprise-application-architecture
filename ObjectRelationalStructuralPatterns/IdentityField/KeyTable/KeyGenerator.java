package ObjectRelationalStructuralPatterns.IdentityField.KeyTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyGenerator {
    private Connection conn;
    private String keyName;
    private long nextId;
    private long maxId;
    private int incrementBy;

    public KeyGenerator(Connection conn, String keyName, int incrementBy) {
        this.conn = conn;
        this.keyName = keyName;
        this.incrementBy = incrementBy;
        nextId = maxId = 0;

        try {
            conn.setAutoCommit(false);
        } catch (SQLException exc) {
            throw new ApplicationException("Unable to turn off autocommit", exc);
        }
    }

    public synchronized Long nextKey() {
        if(nextId == maxId) {
            reserveIds();
        }
        return new Long(nextId++);
    }

    private void reserveIds() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long newNextId;
        try {
            /* In this case we use SELECT... FOR UPDATE to tell the database to hold a write lock
            on the key table. This is an Oracle-specific statement, so your mileage will vary
            if you’re using something else. If you can’t write-lock on the select, you run the
            risk of the transaction failing should another one get in there before you. In this
            case, however, you can pretty safely just rerun reserveIds until you get a pristine
            set of keys. */
            stmt = conn.prepareStatement("SELECT nextID FROM keys WHERE name = ? FOR UPDATE");
            stmt.setString(1, keyName);
            rs = stmt.executeQuery();
            rs.next();
            newNextId = rs.getLong(1);
        } catch (SQLException exc) {
            throw new ApplicationException("Unable to generate ids", exc);
        } finally {
            DB.cleanUp(stmt, rs);
        }

        long newMaxId = newNextId + incrementBy;
        stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE keys SET nextID = ? WHERE name = ?");
            stmt.setLong(1, newMaxId);
            stmt.setString(2, keyName);
            stmt.executeUpdate();
            conn.commit();
            nextId = newNextId;
            maxId = newMaxId;
        } catch (SQLException exc) {
            throw new ApplicationException("Unable to generate ids", exc);
        } finally {
            DB.cleanUp(stmt);
        }
    }
}
