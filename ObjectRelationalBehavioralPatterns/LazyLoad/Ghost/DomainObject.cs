class DomainObject {
    LoadStatus status;
    public DomainObject(long key) {
        this.Key = key;
    }

    public Boolean IsGhost {
        get {return Status == LoadStatus.GHOST;}
    }

    public Boolean IsLoaded {
        get {return Status = LoadStatus.LOADED;}
    }

    public void MarkLoading() {
        Debug.Assert(IsGhost);
        Status = LoadStatus.LOADING;
    }

    public void MarkLoaded() {
        Debug.Assert(Status == LoadStatus.LOADING);
        Status = LoadStatus.LOADED;
    }

    protected void Load() {
        if (IsGhost) {
            DataSource.Load(this);
        }
    }
}

enum LoadStaus {GHOST, LOADING, LOADED};
