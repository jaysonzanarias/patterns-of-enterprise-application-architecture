package BasePatterns.Gateway;

public class GatewayTester {
    public void testSendNullArg() {
        try {
            gate().sendConfirmation(null, 5, "US");
            fail("Didn’t detect null argument");
        } catch (NullPointerException expected) {
        }
        assertEquals(0, gate().getNumberOfMessagesSent());
    }

    private MessageGatewayStub gate() {
        return (MessageGatewayStub) Environment.getMessageGateway();
    }

    protected void setUp() throws Exception {
        Environment.testInit();
    }
}
