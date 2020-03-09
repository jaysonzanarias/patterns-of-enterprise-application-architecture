class AbstractPlayerMapper {
    protected override String TableName {
        get {
            return "Players";
        }
    }

    abstract public String TypeCode {
        get;
    }

    protected override void Load(DomainObject obj, DataRow row) {
        base.Load(obj, row);
        Player player = (Player) obj;
        player.name = (String) row["name"];
    }

    protected override void Save(DomainObject obj, DataRow row) {
        Player player = (Player) obj;
        row["name"] = player.name;
        row["type"] = TypeCode;
    }
}