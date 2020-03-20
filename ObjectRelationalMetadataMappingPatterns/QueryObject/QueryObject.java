package ObjectRelationalMetadataMappingPatterns.QueryObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryObject {
    private Class klass;
    private List criteria = new ArrayList();

    public Set execute(UnitOfWork uow) {
        this.uow = uow;
        return uow.getMapper(klass).findObjectsWhere(generateWhereClause());
    }

    private String generateWhereClause() {
        StringBuffer result = new StringBuffer();
        for (Iterator it = criteria.iterator(); it.hasNext();) {
            Criteria c = (Criteria)it.next();
            if (result.length() != 0)
                result.append(" AND ");
            result.append(c.generateSql(uow.getMapper(klass).getDataMap()));
        }
        return result.toString();
    }
}
