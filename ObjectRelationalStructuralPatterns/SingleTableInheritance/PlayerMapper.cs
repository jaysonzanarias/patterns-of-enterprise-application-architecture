class PlayerMapper {
    private BowlerMapper bmapper;
    private CricketerMapper cmapper;
    private FootballerMapper fmapper;

    public PlayerMapper(Gateway gateway): base(gateway) {
        bmapper = new BowlerMapper(Gateway);
        cmapper = new CricketerMapper(Gateway);
        fmapper = new FootballerMapper(Gateway);
    }

    public Player Find (long key) {
        DataRow row = FindRow(key);
        if (row == null)
            return null;
        else {
            String typecode = (String) row["type"];
            switch (typecode){
                case BowlerMapper.TYPE_CODE:
                    return (Player) bmapper.Find(row);
                case CricketerMapper.TYPE_CODE:
                    return (Player) cmapper.Find(row);
                case FootballerMapper.TYPE_CODE:
                    return (Player) fmapper.Find(row);
                default:
                    throw new Exception("unknown type");
            }
        }
    }

    public override void Update (DomainObject obj) {
        MapperFor(obj).Update(obj);
    }

    private Mapper MapperFor(DomainObject obj) {
        if (obj is Footballer)
            return fmapper;
        if (obj is Bowler)
            return bmapper;
        if (obj is Cricketer)
            return cmapper;

        throw new Exception("No mapper available");
    }

    public override long Insert (DomainObject obj) {
        return MapperFor(obj).Insert(obj);
    }

    public override void Delete (DomainObject obj) {
        MapperFor(obj).Delete(obj);
    }
}