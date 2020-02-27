package ObjectRelationalBehavioralPatterns.LazyLoad.ValueHolder.SourceCodeExamplesDotNet;

public class ValueHolder {
    private T value;
    private readonly Func<object, T> valueRetrieval;

    // Constructor
    public ValueHolder(Func<object, T> valueRetrieval) {
        valueRetrieval = this.valueRetrieval;
    }

    // We'll use the signature "GetValue" for convention
    public T GetValue(object parameter) {
        if (value == null) {
            value = valueRetrieval(parameter);
        }
        return value;
    }
}
