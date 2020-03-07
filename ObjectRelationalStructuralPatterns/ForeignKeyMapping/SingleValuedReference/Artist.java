package ObjectRelationalStructuralPatterns.ForeignKeyMapping.SingleValuedReference;

public class Artist {
    private String name;
    public Artist(Long ID, String name) {
        super(ID);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
