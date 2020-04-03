package WebPresentationPatterns.ApplicationController;

public class Response {
    private Class domainCommand;
    private String viewUrl;

    public Response(Class domainCommand, String viewUrl) {
        this.domainCommand = domainCommand;
        this.viewUrl = viewUrl;
    }

    public DomainCommand getDomainCommand() {
        try {
            return (DomainCommand) domainCommand.newInstance();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getViewUrl() {
        return viewUrl;
    }
}
