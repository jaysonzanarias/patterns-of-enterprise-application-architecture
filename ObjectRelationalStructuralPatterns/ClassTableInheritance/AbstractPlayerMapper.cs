class AbstractPlayerMapper {
    abstract public String TypeCode {
        get;
    }

    protected static String TABLENAME = "Players";

    protected override void Load(DomainObject obj) {
        DataRow row = Find(obj.Id, tableFor(TABLENAME));
        Player player = (Player) obj;
        player.name = (String) row["name"];
    }

    protected override void Save(DomainObject obj) {
        DataRow row = FindRow(obj.Id, tableFor(TABLENAME));
        Player player = (Player) obj;
        row["name"] = player.name;
        row["type"] = TypeCode;
    }

    protected override void AddRow(DomainObject obj) {
        InsertRow(obj, tableFor(TABLENAME));
    }

    public override void Delete(DomainObject obj) {
        DataRow row = FindRow(obj.Id, tableFor(TABLENAME));
        row.Delete();
    }
}
