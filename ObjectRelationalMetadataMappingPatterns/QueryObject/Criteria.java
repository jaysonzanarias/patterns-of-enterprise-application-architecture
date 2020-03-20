package ObjectRelationalMetadataMappingPatterns.QueryObject;

public class Criteria {
    private String sqlOperator;
    protected String field;
    protected Object value;

    public static Criteria greaterThan(String fieldName, int value) {
        return Criteria.greaterThan(fieldName, new Integer(value));
    }

    public static Criteria greaterThan(String fieldName, Object value) {
        return new Criteria(" > ", fieldName, value);
    }

    private Criteria(String sql, String field, Object value) {
        this.sqlOperator = sql;
        this.field = field;
        this.value = value;
    }

    QueryObject query = new QueryObject(Person.class);
    query.addCriteria(Criteria.greaterThan("numberOfDependents",0));

    public String generateSql(DataMap dataMap) {
        return dataMap.getColumnForField(field) + sqlOperator + value;
    }

    public static Criteria matches(String fieldName, String pattern){
        return new MatchCriteria(fieldName, pattern);
    }
}
