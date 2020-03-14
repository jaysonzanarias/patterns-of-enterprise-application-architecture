class CricketerMapper {
    public override String TableName {
        get {
            return "Cricketers";
        }
    }

    public Cricketer Find(long id) {
        return (Cricketer) AbstractFind(id);
    }

    protected override DomainObject CreateDomainObject(){
        return new Cricketer();
    }

    protected override void Load(DomainObject obj, DataRow row) {
        base.Load(obj,row);
        Cricketer cricketer = (Cricketer) obj;
        cricketer.battingAverage = (double)row["battingAverage"];
    }

    protected override void Save(DomainObject obj, DataRow row) {
        base.Save(obj, row);
        Cricketer cricketer = (Cricketer) obj;
        row["battingAverage"] = cricketer.battingAverage;
    }
}