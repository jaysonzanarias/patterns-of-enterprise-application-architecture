package BasePatterns.Gateway;

import java.util.ArrayList;
import java.util.List;

public class MessageGatewayStub extends MessageGateway {
    protected int doSend(String messageType, Object[] args) {
        int returnCode = isMessageValid(messageType, args);
        if (returnCode == MessageSender.SUCCESS) {
            messagesSent++;
        }
        return returnCode;
    }

    private int isMessageValid(String messageType, Object[] args) {
        if (shouldFailAllMessages) {
            return -999;
        }
        if (!legalMessageTypes().contains(messageType)) {
            return MessageSender.UNKNOWN_MESSAGE_TYPE;
        }
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                return MessageSender.NULL_PARAMETER;
            }
        }
        return MessageSender.SUCCESS;
    }

    public static List legalMessageTypes() {
        List result = new ArrayList();
        result.add(CONFIRM);
        return result;
    }

    private boolean shouldFailAllMessages = false;

    public void failAllMessages() {
        shouldFailAllMessages = true;
    }

    public int getNumberOfMessagesSent() {
        return messagesSent;
    }
}
