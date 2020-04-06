class Album {
    public String Title;
    public Artist Artist;
    public IList Tracks {
    get {return ArrayList.ReadOnly(tracksData);}
    }
    public void AddTrack (Track arg) {
    tracksData.Add(arg);
    }
    public void RemoveTrack (Track arg) {
    tracksData.Remove(arg);
    }
    private IList tracksData = new ArrayList();
}