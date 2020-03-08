package ObjectRelationalStructuralPatterns.DependentMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumMapper {
    protected String findStatement() {
        return "SELECT ID, a.title, t.title as trackTitle" +
               " FROM albums a, tracks t" +
               " WHERE a.ID = ? AND t.albumID = a.ID" +
               " ORDER BY t.seq";
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);
        Album result = new Album(id, title);
        loadTracks(result, rs);
        return result;
    }

    public void loadTracks(Album arg, ResultSet rs) throws SQLException {
        arg.addTrack(newTrack(rs));
        while(rs.next()) {
            arg.addTrack(newTrack(rs));
        }
    }

    private Track newTrack(ResultSet rs) throws SQLException {
        String title = rs.getString(3);
        Track newTrack = new Track(title);
        return newTrack;
    }

    public void update(DomainObject arg) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare("UPDATE albums SET title = ? WHERE id = ?");
            updateStatement.setLong(2, arg.getID().longValue());
            Album album = (Album) arg;
            updateStatement.setString(1, album.getTitle());
            updateStatement.execute();
            updateTracks(Album);
        } catch (SQLException e) {
            throw new ApplicationExeption(e);
        } finally {
            DB.cleanUp(updateStatement);
        }
    }

    public void updateTracks(Album arg) throws SQLException {
        PreparedStatement deleteTracksStatement = null;
        try {
            deleteTracksStatement = DB.prepare("DELETE FROM tracks WHERE albumID = ?");
            deleteTracksStatement.setLong(1, arg.getID().longValue());
            deleteTracksStatement.execute();
            for(int i=0; i<arg.getTracks().length; i++){
                Track track = arg.getTracks()[i];
                insertTrack(track, i + 1, arg);
            }
        } finally {
            DB.cleanUp(deleteTracksStatement);
        }
    }

    public void insertTrack(Track track, int seq, Album album) throws SQLException {
        PreparedStatement insertTrackStatement = null;
        try {
            insertTrackStatement = DB.prepare("INSERT INTO tracks(seq, albumID, title) VALUES(?, ?, ?)";
            insertTrackStatement.setInt(1, seq);
            insertTrackStatement.setLong(2, album.getID().longValue());
            insertTrackStatement.setString(3, track.getTitle()));
            insertTrackStatement.execute();
        } finally {
            DB.cleanUp(insertTrackStatement);
        }
    }
}
