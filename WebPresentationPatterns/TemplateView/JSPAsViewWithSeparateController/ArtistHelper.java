package WebPresentationPatterns.TemplateView.JSPAsViewWithSeparateController;

public class ArtistHelper {
    private Artist artist;

    public ArtistHelper(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return artist.getName();
    }

    public String getAlbumList() {
        StringBuffer result = new StringBuffer();
        result.append("<UL>");
        for (Iterator it = getAlbums().iterator(); it.hasNext();) {
            Album album = (Album) it.next();
            result.append("<LI>");
            result.append(album.getTitle());
            result.append("</LI>");
        }
        result.append("</UL>");
        return result.toString();
    }

    public List getAlbums() {
        return artist.getAlbums();
    }
}
