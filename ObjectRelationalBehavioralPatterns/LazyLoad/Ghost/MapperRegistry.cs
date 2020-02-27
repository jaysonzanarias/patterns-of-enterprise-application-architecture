class MapperRegistry: IDataSource {
    public void Load(DomainObject obj) {
        Mapper(obj.GetType()).Load(obj);
    }

    public static Mapper Mapper(Type type) {
        return (Mapper) instance.mappers[type];
    }

    IDictionary mappers = new HashTable();
}
