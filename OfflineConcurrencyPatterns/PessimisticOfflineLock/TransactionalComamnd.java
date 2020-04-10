package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public class TransactionalComamnd implements Command {
    public TransactionalCommand(Command impl) {
        this.impl = impl;
    }

    public void process() throws Exception {
        beginSystemTransaction();
        try {
            impl.process();
            commitSystemTransaction();
        } catch (Exception e) {
            rollbackSystemTransaction();
            throw e;
        }
    }
}
