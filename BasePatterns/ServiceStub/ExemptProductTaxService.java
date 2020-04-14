package BasePatterns.ServiceStub;

public class ExemptProductTaxService implements TaxService {
    private static final BigDecimal EXEMPT_RATE = new BigDecimal("0.0000");
    private static final BigDecimal FLAT_RATE = new BigDecimal("0.0500");
    private static final String EXEMPT_STATE = "IL";
    private static final String EXEMPT_PRODUCT = "12300";

    public TaxInfo getSalesTaxInfo(String productCode, Address addr, Money saleAmount) {
        if (productCode.equals(EXEMPT_PRODUCT) && addr.getStateCode().equals(EXEMPT_STATE)) {
            return new TaxInfo(EXEMPT_RATE, saleAmount.multiply(EXEMPT_RATE));
        } else {
            return new TaxInfo(FLAT_RATE, saleAmount.multiply(FLAT_RATE));
        }
    }
}
