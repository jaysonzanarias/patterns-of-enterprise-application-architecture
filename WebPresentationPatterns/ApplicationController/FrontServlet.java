package WebPresentationPatterns.ApplicationController;

import java.io.IOException;

public class FrontServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        ApplicationController appController = getApplicationController(request);
        String commandString = (String) request.getParameter("command");
        DomainCommand comm =
            appController.getDomainCommand(commandString, getParameterMap(request));
        comm.run(getParameterMap(request));
        String viewPage =
            "/" + appController.getView(commandString, getParameterMap(request)) + ".jsp";
        forward(viewPage, request, response);
    }
}
