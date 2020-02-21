package DataSourcePatterns.DataMapper.SimpleDatabaseMapper;

public class Person {
    private Long id;
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    public Person(Long id, String lastName, String firstName, int numberOfDependents) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberOfDependents = numberOfDependents;
    }
}
