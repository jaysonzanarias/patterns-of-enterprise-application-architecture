package OfflineConcurrencyPatterns.PessimisticOfflineLock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExclusiveReadLockManagerDBImpl {
    private static final String INSERT_SQL = "insert into lock values(?, ?)";
    private static final String DELETE_SINGLE_SQL =
        "delete from lock where lockableid = ? and ownerid = ?";
    private static final String DELETE_ALL_SQL = "delete from lock where ownerid = ?";
    private static final String CHECK_SQL =
        "select lockableid from lock where lockableid = ? and ownerid = ?";

    public void acquireLock(Long lockable, String owner) throws ConcurrencyException {
        if (!hasLock(lockable, owner)) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                pstmt = conn.prepareStatement(INSERT_SQL);
                pstmt.setLong(1, lockable.longValue());
                pstmt.setString(2, owner);
                pstmt.executeUpdate();
            } catch (SQLException sqlEx) {
                throw new ConcurrencyException("unable to lock " + lockable);
            } finally {
                closeDBResources(conn, pstmt);
            }
        }
    }

    public void releaseLock(Long lockable, String owner) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionManager.INSTANCE.getConnection();
            pstmt = conn.prepareStatement(DELETE_SINGLE_SQL);
            pstmt.setLong(1, lockable.longValue());
            pstmt.setString(2, owner);
            pstmt.executeUpdate();
        } catch (SQLException sqlEx) {
            throw new SystemException("unexpected error releasing lock on " + lockable);
        } finally {
            closeDBResources(conn, pstmt);
        }
    }
}
