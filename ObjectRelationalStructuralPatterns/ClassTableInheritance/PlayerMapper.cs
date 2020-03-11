class PlayerMapper {
    public Player Find(long key) {
        DataRow row = FindRow(key, tableFor(TABLENAME));
        if(row == null) {
            return null;
        } else {
            String typecode = (String) row["type"];
            if(typecode == bmapper.TypeCode)
                return bmapper.Find(key);

            if(typecode == cmapper.TypeCode)
                return cmapper.Find(key);

            if(typecode == fmapper.TypeCode)
                return fmapper.Find(key);

            throw new Exception("unknown type");
        }
    }

    protected static String TABLENAME = "Players";

    public override void Update(DomainObject obj) {
        MapperFor(obj).Update(obj);
    }

    private Mapper MapperFor(DomainObject obj) {
        if(obj is Footballer)
            return fmapper;

        if(obj is Bowler)
            return bmapper;

        if(obj is Cricketer)
            return cmapper;

        throw new Exception("No mapper available");
    }

    public override long Insert(DomainObject obj) {
        return MapperFor(obj).Insert(obj);
    }

    override public void Delete(DomainObject obj) {
        MapperFOr(obj).Delete(obj);
    }
}
