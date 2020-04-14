package BasePatterns.ServiceStub;

public interface TaxService {
    public static final TaxService INSTANCE =
        (TaxService) PluginFactory.getPlugin(TaxService.class);
    public TaxInfo getSalesTaxInfo(String productCode, Address addr, Money saleAmount);
}
