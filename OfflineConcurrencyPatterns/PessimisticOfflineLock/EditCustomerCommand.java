package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public class EditCustomerCommand implements Command {
    public void process() throws Exception {
        startNewBusinessTransaction();
        Long customerId = new Long(getReq().getParameter("customer_id"));
        ExclusiveReadLockManager.INSTANCE.acquireLock(
            customerId, AppSessionManager.getSession().getId());
        Mapper customerMapper = MapperRegistry.INSTANCE.getMapper(Customer.class);
        Customer customer = (Customer) customerMapper.find(customerId);
        getReq().getSession().setAttribute("customer", customer);
        forward("/editCustomer.jsp");
    }
}
