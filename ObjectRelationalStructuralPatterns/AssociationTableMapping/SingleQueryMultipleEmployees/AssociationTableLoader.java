package ObjectRelationalStructuralPatterns.AssociationTableMapping.SingleQueryMultipleEmployees;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssociationTableLoader {
    private AbstractMapper sourceMapper;
    private Adder targetAdder;
    public AssociationTableLoader(AbstractMapper primaryMapper, Adder targetAdder) {
        this.sourceMapper = primaryMapper;
        this.targetAdder = targetAdder;
    }

    protected List run(String sql) {
        loadData(sql);
        addAllNewObjectsToIdentityMap();
        return formResult();
    }

    private ResultSet rs = null;
    private void loadData(String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = DB.prepare(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                loadRow();
            }
        } catch(SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    private List resultIds = new ArrayList();
    private Map inProgress = new HashMap();
    private void loadRow() throws SQLException {
        Long ID = new Long(rs.getLong(1));
        if(!resultIds.contains(ID)) {
            resultIds.add(ID);
        }
        if(!sourceMapper.hasLoaded(ID)) {
            if(!inProgress.keySet().contains((ID))) {
                inProgress.put(ID, sourceMapper.loadRow(ID, rs));
            }
            targetAdder.add((DomainObject) inProgress.get(ID), rs);
        }
    }

    public static interface Adder {
        void add(DomainObject host, ResultSet rs) throws SQLException;
    }

    private void addAllNewObjectsToIdentityMap() {
        for(Iterator it = inProgress.values().iterator(); it.hasNext()) {
            sourceMapper.putAsLoaded((DomainObject) it.next());
        }
    }

    private List formResult() {
        List result = new ArrayList();
        for(Iterator it = resultIds.iterator(); it.hasNext()) {
            Long id = (Long) it.next();
            result.add(sourceMapper.lookUp(id));
        }
        return result;
    }
}
