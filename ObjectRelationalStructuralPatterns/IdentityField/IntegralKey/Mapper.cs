class Mapper {
    protected DomainObject AbstractFind(long id) {
        DataRow row = FindRow(id);
        return (row == null) ? null : Find(row);
    }

    protected DataRow FindRow(long id) {
        String filter = String.Format("id = {0}", id);
        DataRow[] results = table.Select(filter);
        return (results.Length == 0) ? null : results[0];
    }

    public DomainObject Find(DataRow row) {
        DomainObject result = CreateDomainObject();
        Load(result, row);
        return result;
    }

    abstract protected DomainObject CreateDomainObject();

    public virtual long Insert(DomainObject arg) {
        DataRow row = table.NewRow();
        arg.Id = GetNextID();
        row["id"] = arg.Id;
        Save(arg, row);
        table.Rows.Add(row);
        return arg.Id;
    }
}