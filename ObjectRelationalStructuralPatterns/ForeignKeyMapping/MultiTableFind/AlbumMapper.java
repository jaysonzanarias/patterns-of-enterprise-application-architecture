package ObjectRelationalStructuralPatterns.ForeignKeyMapping.MultiTableFind;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumMapper {
    public Album find(Long id) {
        return (Album) abstractFind();
    }

    protected String findStatement() {
        return "SELECT a.ID, a.title, a.artistID, r.name " +
               "FROM albums a, artists r " +
               "WHERE ID = ? AND a.artistID = r.ID";
    }

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);
        long artistID = rs.getLong(3);

        ArtistMapper artistMapper = MapperRegistry.artist();
        Artist artist;

        if(artistMapper.isLoaded(artistID)) {
            artist = artistMapper.find(artistID);
        } else {
            artist = loadArtist(artistID, rs);
        }

        Album result = new Album(id, title, artist);
        return result;
    }

    private Artist loadArtist(long id, ResultSet rs) throws SQLException {
        String name = rs.getString(4);
        Artist result = new Artist(new Long(id), name);
        MapperRegistry.artist().register(result.getID(), result);
        return result;
    }
}
