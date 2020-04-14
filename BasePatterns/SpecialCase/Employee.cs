class Employee {

    public virtual String Name {
        get {return _name;}
        set {_name = value;}
    }

    private String _name;

    public virtual Decimal GrossToDate {
        get {return calculateGrossFromPeriod(0);}
    }

    public virtual Contract Contract {
        get {return _contract;}
    }
    private Contract _contract;

}