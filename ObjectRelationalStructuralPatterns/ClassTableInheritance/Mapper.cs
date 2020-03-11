class Mapper {
    public DomainObject AbstractFind(long id, String tablename) {
        DataRow row = FindRow(id, tableFor(tablename));
        if(row == null) {
            return null;
        } else {
            DomainObject result = CreateDomainObject();
            result.Id = id;
            Load(result);
            return result;
        }
    }

    protected DataTable tableFor(String name) {
        return Gateway.Data.Tables[name];
    }

    protected DataRow FindRow(long id, DataTable table) {
        String filter = String.Format("id = {0}", id);
        DataRow[] results = table.Select(filter);
        return (results.Length == 0) ? null: results[0];
    }

    protected DataRow FindRow(long id, String tablename) {
        return FindRow(id, tableFor(tablename));
    }

    protected abstract DomainObject CreateDomainObject();

    public virtual void Update(DomainObject arg) {
        Save(arg);
    }

    public virtual long Insert(DomainObject obj) {
        obj.Id = GetNextID();
        AddRow(obj);
        Save(obj);
        return obj.Id;
    }

    abstract protected void AddRow(DomainObject obj);
    protected virtual void InsertRow(DomainObject arg, DataTable table) {
        DataRow row = table.NewRow();
        row["id"] = arg.Id;
        table.Rows.Add(row);
    }

    public abstract void Delete(DomainObject obj);
}
