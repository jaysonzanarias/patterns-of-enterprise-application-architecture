package ObjectRelationalMetadataMappingPatterns.MetadataMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DataMap {
    private Class domainClass;
    private String tableName;
    private List columnMaps = new ArrayList();

    public String columnList() {
        StringBuffer result = new StringBuffer(" ID");
        for (Iterator it = columnMaps.iterator(); it.hasNext(); ) {
            result.append(",");
            ColumnMap columnMap = (ColumnMap) it.next();
            result.append(columnMap.getColumnName());
        }
        return result.toString();
    }

    public String getTableName() {
        return tableName;
    }

    public String updateList() {
        StringBuffer result = new StringBuffer(" SET ");
        for (Iterator it = columnMaps.iterator(); it.hasNext(); ) {
            ColumnMap columnMap = (ColumnMap) it.next();
            result.append(columnMap.getColumnName());
            result.append("=?,");
        }
        result.setLength(result.length() - 1);
        return result.toString();
    }

    public Iterator getColumns() {
        return Collections.unmodifiableCollection(columnMaps).iterator();
    }

    public String insertList() {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < columnMaps.size(); i++) {
            result.append(",");
            result.append("?");
        }
        return result.toString();
    }
}
