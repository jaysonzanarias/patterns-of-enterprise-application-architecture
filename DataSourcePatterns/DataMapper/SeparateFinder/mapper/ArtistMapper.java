package DataSourcePatterns.DataMapper.SeparateFinder.mapper;

import DataSourcePatterns.DataMapper.SeparateFinder.domain.ArtistFinder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistMapper extends AbstractMapper implements ArtistFinder {
    @Override
    public Artist find(Long id) {
        return null;
    }

    @Override
    public Artist find(long id) {
        return null;
    }

    @Override
    protected String findStatement() {
        return "SELECT " + COLUMN_LIST + " FROM artists art WHERE ID = ?";
    }

    public static String COLUMN_LIST = "art.ID, art.name";

    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        Artist result = new Artist(id, name);
        return result;
    }
}
