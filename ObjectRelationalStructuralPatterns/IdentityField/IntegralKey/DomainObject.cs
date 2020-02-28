package ObjectRelationalStructuralPatterns.IdentityField.IntegralKey;

public class DomainObject {
    public const long PLACEHOLDER_ID = -1;
    public long Id = PLACEHOLDER_ID;

    public Boolean isNew() {
        return Id == PLACEHOLDER_ID;
    }
}
