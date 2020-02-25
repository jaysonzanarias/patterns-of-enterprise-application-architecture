package ObjectRelationalBehavioralPatterns.UnitOfWork;

// explicit Unit of Work managemen
public class EditAlbumScript {
    public static void updateTitle(Long albumId, String title) {
        UnitOfWork.newCurrent();
        Mapper mapper = MapperRegistry.getMapper(Album.class);
        Album album = (Album) mapper.find(albumId);
        album.setTitle(title);
        UnitOfWork.getCurrent().commit();
    }
}
