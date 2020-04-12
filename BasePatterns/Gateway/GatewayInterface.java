package BasePatterns.Gateway;

public interface GatewayInterface {
    int send(String messageType, Object[] args);

    // better
    public void sendConfirmation(String orderID, int amount, String symbol);
}
