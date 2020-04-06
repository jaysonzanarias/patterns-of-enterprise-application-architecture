class AlbumAssembler {
    public AlbumDTO WriteDTO (Album subject) {
        AlbumDTO result = new AlbumDTO();
        result.Artist = subject.Artist.Name;
        result.Title = subject.Title;
        ArrayList trackList = new ArrayList();

        foreach (Track t in subject.Tracks)
           trackList.Add (WriteTrack(t));

        result.Tracks = (TrackDTO[]) trackList.ToArray(typeof(TrackDTO));

        return result;
    }

    public TrackDTO WriteTrack (Track subject) {
        TrackDTO result = new TrackDTO();
        result.Title = subject.Title;
        result.Performers = new String[subject.Performers.Count];
        ArrayList performerList = new ArrayList();

        foreach (Artist a in subject.Performers)
            performerList.Add (a.Name);

        result.Performers = (String[]) performerList.ToArray(typeof (String));
        return result;
    }
}