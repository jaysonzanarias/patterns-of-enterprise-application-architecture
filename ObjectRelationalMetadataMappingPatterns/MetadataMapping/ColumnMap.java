package ObjectRelationalMetadataMappingPatterns.MetadataMapping;

public class ColumnMap {
    private String columnName;
    private String fieldName;
    private Field field;
    private DataMap dataMap;

    public ColumnMap(String columnName, String fieldName, DataMap dataMap) {
        this.columnName = columnName;
        this.fieldName = fieldName;
        this.dataMap = dataMap;
        initField();
    }

    private void initField() {
        try {
            field = dataMap.getDomainClass().getDeclaredField(getFieldName());
            field.setAccessible(true);
        } catch (Exception e) {
            throw new ApplicationException("unable to set up field: " + fieldName, e);
        }
    }

    public void setField(Object result, Object columnValue) {
        try {
            field.set(result, columnValue);
        } catch (Exception e) {
            throw new ApplicationException("Error in setting " + fieldName, e);
        }
    }

    public Object getValue(Object subject) {
        try {
            return field.get(subject);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getColumnName() {
        return columnName;
    }
}
