package BasePatterns.Gateway;

public class MessageGateway {
    protected static final String CONFIRM = "CNFRM";
    private MessageSender sender;

    public void sendConfirmation(String orderID, int amount, String symbol) {
        Object[] args = new Object[] { orderID, new Integer(amount), symbol };
        send(CONFIRM, args);
    }

    private void send(String msg, Object[] args) {
        int returnCode = doSend(msg, args);
        if (returnCode == MessageSender.NULL_PARAMETER) {
            throw new NullPointerException("Null Parameter passed for msg type: " + msg);
        }
        if (returnCode != MessageSender.SUCCESS) {
            throw new IllegalStateException(
                "Unexpected error from messaging system #:" + returnCode);
        }
    }

    protected int doSend(String msg, Object[] args) {
        Assert.notNull(sender);
        return sender.send(msg, args);
    }
}
