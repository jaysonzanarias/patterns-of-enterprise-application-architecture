package ObjectRelationalStructuralPatterns.ForeignKeyMapping.SingleValuedReference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumMapper extends AbstractMapper {
    public Album find(Long id) {
        return (Album) abstractFind(id);
    }

    protected String findStatement() {
        return "SELECT ID, title, artistID FROM albums WHERE ID = ?";
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);
        long artistID = rs.getLong(3);
        Artist artist = MapperRegistry.artist().find(artistID);
        Album result = new Album(id, title, artist);
        return result;
    }

    public void update(DomainObject arg) {
        PreparedStatement statement = null;
        try {
            statement = DB.prepare("UPDATE albums SET title = ?, artistID = ? WHERE id = ?");
            statement.setLong(3, arg.getID().longValue());
            Album album = (Album) arg;
            statement.setString(1, album.getTitle());
            statement.setLong(2, album.getArtist().getID().longValue());
            statement.execute();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            cleanUp(statement);
        }
    }
}
