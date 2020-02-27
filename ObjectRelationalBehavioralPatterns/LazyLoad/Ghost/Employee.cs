package ObjectRelationalBehavioralPatterns.LazyLoad.Ghost;

public class Employee {
    public String name {
        get {
            Load();
            return _name;
        }

        set {
            Load();
            _name = value;
        }
    }
    String _name;
}
