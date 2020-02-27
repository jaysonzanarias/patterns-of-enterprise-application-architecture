class DataSource {
    public static void Load(DomainObject obj) {
        instance.Load(obj);
    }

    public interface IDataSource {
        void Load(DomainObject obj);
    }
}
