package DataSourcePatterns.DataMapper.SeparateFinder.domain;

public interface ArtistFinder {
    Artist find(Long id);
    Artist find(long id);
}
