package BasePatterns.ServiceStub;

public class ChargeGenerator {
    public Charge[] calculateCharges(BillingSchedule schedule) {
        List charges = new ArrayList();
        Charge baseCharge = new Charge(schedule.getBillingAmount(), false);
        charges.add(baseCharge);
        TaxInfo info =
            TaxService.INSTANCE.getSalesTaxInfo(schedule.getProduct(), schedule.getAddress(),
                                                schedule.getBillingAmount());
        if (info.getStateRate().compareTo(new BigDecimal(0)) > 0) {
            Charge taxCharge = new Charge(info.getStateAmount(), true);
            charges.add(taxCharge);
        }
        return (Charge[]) charges.toArray(new Charge[charges.size()]);
    }
}
