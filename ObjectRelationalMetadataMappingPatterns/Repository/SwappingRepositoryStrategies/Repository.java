package ObjectRelationalMetadataMappingPatterns.Repository.SwappingRepositoryStrategies;

public class Repository {
    private RepositoryStrategy strategy;
    protected List matching(Criteria aCriteria) {
        return strategy.matching(aCriteria);
    }
}
