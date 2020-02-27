class DomainList {
    IList data {
        get {
            Load();
            return _data;
        }
        set {_data = value;}
    }

    IList _data = new ArrayList();

    public int Count {
        get {return data.Count;}
    }

    public void Load() {
        if (IsGhost) {
            MarkLoading();
            RunLoader(this);
            MarkLoaded();
        }
    }

    public delegate void Loader(DomainList list);
    public Loader RunLoader;
}
