package DataSourcePatterns.DataMapper.SeparateFinder.domain;

import java.util.List;

public interface TrackFinder {
    Track find(Long id);
    Track find(long id);
    List findForAlbum(Long albumID);
}
