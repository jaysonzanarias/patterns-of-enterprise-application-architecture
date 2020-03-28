package ObjectRelationalMetadataMappingPatterns.Repository.SwappingRepositoryStrategies;

public class RelationalStrategy implements RepositoryStrategy {
    protected List matching(Criteria criteria) {
        Query query = new Query(myDomainObjectClass()) query.addCriteria(criteria);
        return query.execute(unitOfWork());
    }
}