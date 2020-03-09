class Mapper {
    protected DataTable table {
        get {
            return Gateway.Data.Tables[TableName];
        }
    }

    protected Gateway Gateway;
    abstract protected String TableName {
        get;
    }

    protected DomainObject AbstractFind(long id) {
        DataRow row = FindRow(id);
        return (row == null) ? null: Find(row);
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

    protected virtual void Load(DomainObject obj, DataRow row) {
        obj.Id = (int) row ["id"];
    }

    public virtual void Update (DomainObject arg) {
        Save (arg, FindRow(arg.Id));
    }

    public virtual long Insert (DomainObject arg) {
        DataRow row = table.NewRow();
        arg.Id = GetNextID();
        row["id"] = arg.Id;
        Save (arg, row);
        table.Rows.Add(row);
        return arg.Id;
    }

    public virtual void Delete(DomainObject obj) {
        DataRow row = FindRow(obj.Id);
        row.Delete();
    }
}