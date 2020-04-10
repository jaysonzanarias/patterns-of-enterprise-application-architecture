package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public class SaveCustomerCommand implements Command {
    public void process() throws Exception {
        continueBusinessTransaction();
        Customer customer = (Customer) getReq().getSession().getAttribute("customer");
        String name = getReq().getParameter("customerName");
        customer.setName(name);
        Mapper customerMapper = MapperRegistry.INSTANCE.getMapper(Customer.class);
        customerMapper.update(customer);
        ExclusiveReadLockManager.INSTANCE.releaseLock(customer.getId(),
                                                      AppSessionManager.getSession().getId());
        forward("/customerSaved.jsp");
    }
}
