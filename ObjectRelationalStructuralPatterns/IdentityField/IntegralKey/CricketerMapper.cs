class CricketerMapper {
    public Cricketer Find(long id) {
        return (Cricketer) AbstractFind(id);
    }
}