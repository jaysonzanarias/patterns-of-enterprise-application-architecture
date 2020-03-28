package ObjectRelationalMetadataMappingPatterns.Repository.FindingAPersonsDependents;

public class Person {
    public List dependents() {
        Repository repository = Registry.personRepository();
        Criteria criteria = new Criteria();
        criteria.equal(Person.BENEFACTOR, this);
        return repository.matching(criteria);
    }
}
