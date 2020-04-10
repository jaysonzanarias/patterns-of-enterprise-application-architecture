package OfflineConcurrencyPatterns.CoarseGrainedLock.SharedOptimisticOfflineLock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Version {
    private Long id;
    private long value;
    private String modifiedBy;
    private Timestamp modified;
    private boolean locked;
    private boolean isNew;
    private static final String UPDATE_SQL =
        "UPDATE version SET VALUE = ?, modifiedBy = ?, modified = ? " +
        "WHERE id = ? and value = ?";
    private static final String DELETE_SQL = "DELETE FROM version WHERE id = ? and value = ?";
    private static final String INSERT_SQL = "INSERT INTO version VALUES (?, ?, ?, ?)";
    private static final String LOAD_SQL =
        "SELECT id, value, modifiedBy, modified FROM version WHERE id = ?";

    public static Version find(Long id) {
        Version version = AppSessionManager.getSession().getIdentityMap().getVersion(id);
        if (version == null) {
            version = load(id);
        }
        return version;
    }

    private static Version load(Long id) {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        Version version = null;
        try {
            conn = ConnectionManager.INSTANCE.getConnection();
            pstmt = conn.prepareStatement(LOAD_SQL);
            pstmt.setLong(1, id.longValue());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                long value = rs.getLong(2);
                String modifiedBy = rs.getString(3);
                Timestamp modified = rs.getTimestamp(4);
                version = new Version(id, value, modifiedBy, modified);
                AppSessionManager.getSession().getIdentityMap().putVersion(version);
            } else {
                throw new ConcurrencyException("version " + id + " not found.");
            }
        } catch (SQLException sqlEx) {
            throw new SystemException("unexpected sql error loading version", sqlEx);
        } finally {
            cleanupDBResources(rs, conn, pstmt);
        }
        return version;
    }

    public static Version create() {
        Version version =
            new Version(IdGenerator.INSTANCE.nextId(), 0, AppSessionManager.getSession().getUser(),
                        now());
        version.isNew = true;
        return version;
    }

    public void insert() {
        if (isNew()) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                pstmt = conn.prepareStatement(INSERT_SQL);
                pstmt.setLong(1, this.getId().longValue());
                pstmt.setLong(2, this.getValue());
                pstmt.setString(3, this.getModifiedBy());
                pstmt.setTimestamp(4, this.getModified());
                pstmt.executeUpdate();
                AppSessionManager.getSession().getIdentityMap().putVersion(this);
                isNew = false;
            } catch (SQLException sqlEx) {
                throw new SystemException("unexpected sql error inserting version", sqlEx);
            } finally {
                cleanupDBResources(conn, pstmt);
            }
        }
    }

    public void increment() throws ConcurrencyException {
        if (!isLocked()) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                pstmt = conn.prepareStatement(UPDATE_SQL);
                pstmt.setLong(1, value + 1);
                pstmt.setString(2, getModifiedBy());
                pstmt.setTimestamp(3, getModified());
                pstmt.setLong(4, id.longValue());
                pstmt.setLong(5, value);
                int rowCount = pstmt.executeUpdate();
                if (rowCount == 0) {
                    throwConcurrencyException();
                }
                value++;
                locked = true;
            } catch (SQLException sqlEx) {
                throw new SystemException("unexpected sql error incrementing version", sqlEx);
            } finally {
                cleanupDBResources(conn, pstmt);
            }
        }
    }

    private void throwConcurrencyException() {
        Version currentVersion = load(this.getId());
        throw new ConcurrencyException("version modified by " + currentVersion.modifiedBy + " at " +
                                       DateFormat.getDateTimeInstance()
                                                 .format(currentVersion.getModified()));
    }
}
