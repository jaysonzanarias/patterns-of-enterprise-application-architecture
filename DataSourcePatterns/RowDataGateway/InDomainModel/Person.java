package DataSourcePatterns.RowDataGateway.InDomainModel;

public class Person {
    private PersonGateway data;
    public Person(PersonGateway data) {
        this.data = data;
    }

    public int getNumberOfDependents() {
        return data.getNumberOfDependents();
    }

    public Money getExemption() {
        Money baseExemption = Money.dollars(1500);
        Money dependentExemption = Money.dollars(750);
        return baseExemption.add(dependentExemption.multiply(this.getNumberOfDependents()));
    }
}
