package ObjectRelationalMetadataMappingPatterns.Repository.FindingAPersonsDependents;

public class PersonRepository {
    public List list dependentsOf(Person aPerson) {
        Criteria criteria = new Criteria();
        criteria.equal(Person.BENEFACTOR, aPerson);
        return matching(criteria);
    }
}

public class Person {
    public List dependents() {
        return Registry.personRepository().dependentsOf(this);
    }
}