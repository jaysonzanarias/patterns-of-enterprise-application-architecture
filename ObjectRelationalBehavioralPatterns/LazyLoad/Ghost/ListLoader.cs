class ListLoader {
    public String Sql;
    public IList SqlParams = new ArrayList();
    public Mapper Mapper;

    public void Attach(DomainList list) {
        list.RunLoader = new DomainList.Loader(Load);
    }

    public void Load(DomainList list) {
        list.IsLoaded = true;
        IDbCommand comm = new OleDbCommand(Sql, DB.connection);
        foreach(Object param in SqlParams)
            comm.Parameters.Add(new OleDbParameter(param.ToString(), param));

        IDataReader reader = comm.ExecuteReader();

        while(reader.Read()) {
            DomainObject obj = GhostForLine(reader);
            Mapper.LoadLine(reader, obj);
            list.Add(obj);
        }

        reader.Close();
    }

    private DomainObject GhostForLine(IDataReader reader) {
        return Mapper.AbstractFind((System.Int32) reader[Mapper.KeyColumnName]);
    }
}
