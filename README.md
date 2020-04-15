# Patterns of Enterprise Application Architecture
Compilation of sample codes from the book Patterns of Enterprise Application Architecture by Martin Fowler.

I used and created this repo when I studied the book.

Super worth reading it! Read the book and learn more on each topic.

Enjoy! :)

## Layering
- Presentation
- Domain Logic
- Data Source

## Patterns
Domain Logic Patterns
- Transaction Script
- Domain Model
- Table Module
- Service Layer

Data Source Patterns
- Table Data Gateway
- Row Data Gateway
- Active Records
- Data Mapper

Object-Relational Behavior Patterns
- Unit of Work
- Identify Map
- Lazy Load

Object-Relational Structural Patterns
- Identity Field
- Foreign Key Mapping
- Association Table Mapping
- Dependent Mapping
- Embedded Value
- Serialized LOB
- Single Table Inheritance
- Class Table Inheritance
- Concrete Table Inheritance
- Inheritance Mappers

Object-Relational Metadata Mapping Patterns
- Metadata Mapping
- Query Object
- Repository

Web Presentation Patterns
- Model View Controller
- Page Controller
- Front Controller
- Template View
- Transform View
- Two Step View
- Application Controller

Distribution Patterns
- Remote Facade
- Data Transfer Object

Offline Concurrency Patterns
- Optimistic Offline Lock
- Pessimistic Offline Lock
- Coarse-Grained Lock
- Implicit Lock

Session State Patterns
- Client Session State
- Server Session State
- Database Session State

Base Patterns
- Gateway
- Mapper
- Layer Supertype
- Separated Interface
- Registry
- Value Object
- Money
- Special Case
- Plugin
- Service Stub
- Record Set

## Details
Domain Logic Patterns
- Transaction Script
    > Organizes business logic by procedures where each procedure handles a single request from the presentation.
    <br>![Screenshot](images/TransactionScript.jpg)

- Domain Model
    > An object model of the domain that incorporates both behavior and data.
    <br>![Screenshot](images/DomainModel.jpg)

- Table Module
    > A single instance that handles the business logic for all rows in a database table or view.
    <br>![Screenshot](images/TableModule.jpg)

- Service Layer
    > Defines an application’s boundary with a layer of services that establishes a set of available operations and coordinates the application’s response in each operation.
    <br>![Screenshot](images/ServiceLayer.png)

Data Source Patterns
- Table Data Gateway
    > An object that acts as a Gateway to a database table. One instance handles all the rows in the table.
    <br>![Screenshot](images/TableDataGateway.png)

- Row Data Gateway
    > An object that acts as a Gateway to a single record in a data source. There is one instance per row.
    <br>![Screenshot](images/RowDataGateway.png)

- Active Records
    > An object that wraps a row in a database table or view, encapsulates the database access, and adds domain logic on that data.
    <br>![Screenshot](images/ActiveRecords.png)

- Data Mapper
    > A layer of Mappers (473) that moves data between objects and a database while keeping them independent of each other and the mapper itself.
    <br>![Screenshot](images/DataMapper.png)

Object-Relational Behavior Patterns
- Unit of Work
    > Maintains a list of objects affected by a business transaction and coordinates the writing out of changes and the resolution of concurrency problems.
    <br>![Screenshot](images/UnitOfWork.png)

- Identify Map
    > Ensures that each object gets loaded only once by keeping every loaded object in a map. Looks up objects using the map when referring to them.
    <br>![Screenshot](images/IdentityMap.png)

- Lazy Load
    > An object that doesn't contain all of the data you need but knows how to get it.
    <br>![Screenshot](images/LazyLoad.png)

    - Types:
        - Lazy Initialization
        - Virtual Proxy
        - Value Holder
        - Ghost

Object-Relational Structural Patterns
- Identity Field
    > Saves a database ID field in an object to maintain identity between an in-memory object and a database row.
    <br>![Screenshot](images/IdentityField.png)

    - Types:
        - Integral
        - Key Table
        - Compound Key

- Foreign Key Mapping
    > Maps an association between objects to a foreign key reference between tables.
    <br>![Screenshot](images/ForeignKeyMapping.png)
    - Types:
        - Single-Valued Reference
        - Multi Table Find
        - Collection of References

- Association Table Mapping
    > Saves an association as a table with foreign keys to the tables that are linked by the association.
    <br>![Screenshot](images/AssociationTableMapping.png)

- Dependent Mapping
    > Has one class perform the database mapping for a child class.
    <br>![Screenshot](images/DependentMapping.png)

- Embedded Value
    > Maps an object into several fields of another object’s table.
    <br>![Screenshot](images/EmbeddedValue.png)

- Serialized LOB
    > Saves a graph of objects by serializing them into a single large object (LOB), which it stores in a database field.
    <br>![Screenshot](images/SerializedLOB.png)

- Single Table Inheritance
    > Represents an inheritance hierarchy of classes as a single table that has columns for all the fields of the various classes.
    <br>![Screenshot](images/SingleTableInheritance.png)

- Class Table Inheritance
    > Represents an inheritance hierarchy of classes with one table for each class.
    <br>![Screenshot](images/ClassTableInheritance.png)

- Concrete Table Inheritance
    > Represents an inheritance hierarchy of classes with one table per concrete class in the hierarchy.
    <br>![Screenshot](images/ConcreteTableInheritance.png)

- Inheritance Mappers
    > A structure to organize database mappers that handle inheritance hierarchies.
    <br>![Screenshot](images/InheritanceMappers.png)

Object-Relational Metadata Mapping Patterns
- Metadata Mapping
    > Holds details of object-relational mapping in metadata.
    <br>![Screenshot](images/MetadataMapping.png)

- Query Object
    > An object that represents a database query.
    <br>![Screenshot](images/QueryObject.png)

- Repository
    > Mediates between the domain and data mapping layers using a collection-like interface for accessing domain objects.
    <br>![Screenshot](images/Repository.png)

Web Presentation Patterns
- Model View Controller
- Page Controller
- Front Controller
- Template View
- Transform View
- Two Step View
- Application Controller

Distribution Patterns
- Remote Facade
- Data Transfer Object

Offline Concurrency Patterns
- Optimistic Offline Lock
- Pessimistic Offline Lock
- Coarse-Grained Lock
- Implicit Lock

Session State Patterns
- Client Session State
- Server Session State
- Database Session State

Base Patterns
- Gateway
- Mapper
- Layer Supertype
- Separated Interface
- Registry
- Value Object
- Money
- Special Case
- Plugin
- Service Stub
- Record Set

## Additional References
- https://www.sourcecodeexamples.net/p/p-of-eaa.html
- Lazy Load: Ghost
    - https://stackoverflow.com/questions/58243839/how-to-implement-lazy-loading-using-ghost-object-in-java
