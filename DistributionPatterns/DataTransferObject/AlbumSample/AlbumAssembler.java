package DistributionPatterns.DataTransferObject.AlbumSample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlbumAssembler {
    public AlbumDTO writeDTO(Album subject) {
        AlbumDTO result = new AlbumDTO();
        result.setTitle(subject.getTitle());
        result.setArtist(subject.getArtist().getName());
        writeTracks(result, subject);
        return result;
    }

    private void writeTracks(AlbumDTO result, Album subject) {
        List newTracks = new ArrayList();
        Iterator it = subject.getTracks().iterator();
        while (it.hasNext()) {
            TrackDTO newDTO = new TrackDTO();
            Track thisTrack = (Track) it.next();
            newDTO.setTitle(thisTrack.getTitle());
            writePerformers(newDTO, thisTrack);
            newTracks.add(newDTO);
        }
        result.setTracks((TrackDTO[]) newTracks.toArray(new TrackDTO[0]));
    }

    private void writePerformers(TrackDTO dto, Track subject) {
        List result = new ArrayList();
        Iterator it = subject.getPerformers().iterator();
        while (it.hasNext()) {
            Artist each = (Artist) it.next();
            result.add(each.getName());
        }
        dto.setPerformers((String[]) result.toArray(new String[0]));
    }

    public void createAlbum(String id, AlbumDTO source) {
        Artist artist = Registry.findArtistNamed(source.getArtist());
        if (artist == null) {
            throw new RuntimeException("No artist named " + source.getArtist());
        }
        Album album = new Album(source.getTitle(), artist);
        createTracks(source.getTracks(), album);
        Registry.addAlbum(id, album);
    }

    private void createTracks(TrackDTO[] tracks, Album album) {
        for (int i = 0; i < tracks.length; i++) {
            Track newTrack = new Track(tracks[i].getTitle());
            album.addTrack(newTrack);
            createPerformers(newTrack, tracks[i].getPerformers());
        }
    }

    private void createPerformers(Track newTrack, String[] performerArray) {
        for (int i = 0; i < performerArray.length; i++) {
            Artist performer = Registry.findArtistNamed(performerArray[i]);
            if (performer == null) {
                throw new RuntimeException("No artist named " + performerArray[i]);
            }
            newTrack.addPerformer(performer);
        }
    }

    public void updateAlbum(String id, AlbumDTO source) {
        Album current = Registry.findAlbum(id);
        if (current == null) {
            throw new RuntimeException("Album does not exist: " + source.getTitle());
        }
        if (source.getTitle() != current.getTitle()) {
            current.setTitle(source.getTitle());
        }
        if (source.getArtist() != current.getArtist().getName()) {
            Artist artist = Registry.findArtistNamed(source.getArtist());
            if (artist == null) {
                throw new RuntimeException("No artist named " + source.getArtist());
            }
            current.setArtist(artist);
        }
        updateTracks(source, current);
    }

    private void updateTracks(AlbumDTO source, Album current) {
        for (int i = 0; i < source.getTracks().length; i++) {
            current.getTrack(i).setTitle(source.getTrackDTO(i).getTitle());
            current.getTrack(i).clearPerformers();
            createPerformers(current.getTrack(i), source.getTrackDTO(i).getPerformers());
        }
    }
}
