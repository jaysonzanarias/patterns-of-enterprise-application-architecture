class AbstractMapper {
    protected DomainObject AbstractFind(long id) {
        Assert.True (id != DomainObject.PLACEHOLDER_ID);
        DataRow row = FindRow(id);

        return (row == null) ? null: Load(row);
    }

    protected DataRow FindRow(long id) {
        String filter = String.Format("id = {0}", id);
        DataRow[] results = table.Select(filter);

        return (results.Length == 0) ? null: results[0];
    }

    protected DataTable table {
        get {
            return dsh.Data.Tables[TableName];
        }
    }

    public DataSetHolder dsh;
    abstract protected String TableName {
        get;
    }

    protected override String TableName {
        get {
            return "Employees";
        }
    }

    protected DomainObject Load(DataRow row) {
        long id = (int) row["id"];

        if(identityMap[id] != null) {
            return (DomainObject) identityMap[id];
        } else {
            DomainObject result = CreateDomainObject();
            result.Id = id;
            identityMap.Add(result.Id, result);
            doLoad(result, row);
            return result;
        }
    }

    abstract protected DomainObject CreateDomainObject();
    private IDictionary identityMap = new Hashtable();
    abstract protected void doLoad(DomainObject obj, DataRow row);

    public virtual void Update(DomainObject arg) {
        Save(arg, FindRow(arg.Id));
    }
    abstract protected void Save(DomainObject arg, DataRow row);
}