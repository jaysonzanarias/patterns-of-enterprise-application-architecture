# Patterns of Enterprise Application Architecture
Compilation of sample codes from the book Patterns of Enterprise Application Architecture by Martin Fowler.

I used and created this repo when I studied the book. Super worth reading it. 

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
    - Types:
        - Lazy Initialization
        - Virtual Proxy
        - Value Holder
        - Ghost

Object-Relational Structural Patterns
- Identity Field
    - Types:
        - Integral
        - Key Table
        - Compound Key
- Foreign Key Mapping
    - Types:
        - Single-Valued Reference
        - Multi Table Find
        - Collection of References
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

#### Additional References:
- https://www.sourcecodeexamples.net/p/p-of-eaa.html
- Lazy Load: Ghost
    - https://stackoverflow.com/questions/58243839/how-to-implement-lazy-loading-using-ghost-object-in-java
