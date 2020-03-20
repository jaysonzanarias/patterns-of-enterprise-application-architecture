package ObjectRelationalMetadataMappingPatterns.QueryObject;

public class MatchCriteria extends Criteria {
    public String generateSql(DataMap dataMap) {
        return "UPPER(" + dataMap.getColumnForField(field) + ") LIKE UPPER(’" + value + "’)";
    }
}
