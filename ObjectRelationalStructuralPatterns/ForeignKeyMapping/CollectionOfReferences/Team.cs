class Team {
    public String name;
    public IList Players {
        get {
            return ArrayList.ReadOnly(playersData);
        }

        set {
            playersData = new ArrayList(value);
        }
    }

    public void AddPlayer(Player arg) {
        playersData.Add(arg);
    }

    private IList playersData = new ArrayList();
}