package DomainLogicPatterns.ServiceLayer;

public interface EmailGateway {
    void sendEmailMessage(String toAddress, String subject, String body);
}
