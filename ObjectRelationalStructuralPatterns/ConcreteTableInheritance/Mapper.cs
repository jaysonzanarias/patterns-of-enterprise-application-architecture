class Mapper {
    public Gateway Gateway;
    private IDictionary identityMap = new Hashtable();

    public Mapper (Gateway gateway) {
        this.Gateway = gateway;
    }

    private DataTable table {
        get {
            return Gateway.Data.Tables[TableName];
        }
    }

    abstract public String TableName {
        get;
    }

    public DomainObject AbstractFind(long id) {
        DataRow row = FindRow(id);
        if (row == null)
            return null;
        else {
            DomainObject result = CreateDomainObject();
            Load(result, row);
            return result;
        }
    }

    private DataRow FindRow(long id) {
        String filter = String.Format("id = {0}", id);
        DataRow[] results = table.Select(filter);
        if (results.Length == 0)
            return null;
        else
            return results[0];
    }

    protected abstract DomainObject CreateDomainObject();

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