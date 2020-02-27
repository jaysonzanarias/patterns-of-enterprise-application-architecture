class Mapper {
    public DomainObject AbstractFind(long key) {
        DomainObject result;
        result = (DomainObject) loadedMap[key];

        if(result == null) {
            result = CreateGhost(key);
            loadedMap.Add(key, result);
        }

        return result;
    }

    IDictionary loadedMap = new Hashtable();
    public abstract DomainObject CreateGhost(long key);

    public void Load(DomainObject obj) {
        if(! obj.IsGhost) return;

        IDbCommand comm = new OleDbCommand(findStatement(), DB.connection);
        comm.Parameters.Add(new OleDbParameter("key", obj.key));
        IDataReader reader = comm.ExecuteReader();
        reader.Read();
        Loadline (reader, obj);
        reader.Close();
    }

    protected abstract String findStatement();

    public void Loadline(IDataReader reader, DomainObject obj) {
        if (obj.IsGhost) {
            obj.MarkLoading();
            doLoadLine(reader, obj);
            obj.MarkLoaded();
        }
    }

    protected abstract void doLoadLine(IDataReader reader, DomainObject obj);
}
