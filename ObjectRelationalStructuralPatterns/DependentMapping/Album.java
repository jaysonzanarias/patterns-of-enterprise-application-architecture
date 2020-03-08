package ObjectRelationalStructuralPatterns.DependentMapping;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private List tracks = new ArrayList();

    public void addTrack(Track arg) {
        tracks.add(arg);
    }

    public void removeTrack(Track arg) {
        tracks.remove(arg);
    }

    public void removeTrack(int i) {
        tracks.remove(i);
    }

    public Track[] getTracks() {
        return (Track[]) tracks.toArray(new Track[tracks.size()]);
    }
}
