package DataSourcePatterns.DataMapper.SimpleDatabaseMapper;

public interface StatementSource {
    String sql();
    Object[] parameters();
}
