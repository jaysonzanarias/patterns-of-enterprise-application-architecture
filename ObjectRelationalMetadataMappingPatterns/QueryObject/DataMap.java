package ObjectRelationalMetadataMappingPatterns.QueryObject;

import java.util.Iterator;

public class DataMap {
    public String getColumnForField(String fieldName) {
        for (Iterator it = getColumns(); it.hasNext(); ) {
            ColumnMap columnMap = (ColumnMap) it.next();
            if (columnMap.getFieldName().equals(fieldName)) {
                return columnMap.getColumnName();
            }
        }
        throw new ApplicationException("Unable to find column for " + fieldName);
    }
}
