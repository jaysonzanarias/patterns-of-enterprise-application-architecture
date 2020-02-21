package DataSourcePatterns.DataMapper.SeparateFinder.mapper;

import DataSourcePatterns.DataMapper.SeparateFinder.domain.TrackFinder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackMapper extends AbstractMapper implements TrackFinder {
    public static final String findForAlbumStatement = "SELECT ID, seq, albumID, title FROM tracks " +
            "WHERE albumID = ? ORDER BY seq";

    @Override
    public Track find(Long id) {
        return null;
    }

    @Override
    public Track find(long id) {
        return null;
    }

    public List findForAlbum(Long albumID){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findForAlbumStatement);
            stmt.setLong(1, albumID.longValue());
            rs = stmt.executeQuery();

            List result = new ArrayList();
            while(rs.next()) {
                result.add(load(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally { cleanUp(stmt, rs); }
    }

    @Override
    protected String findStatement() {
        return null;
    }

    @Override
    protected DOmainObject doLoad(Long id, ResultSet rs) throws SQLException {
        return null;
    }
}
