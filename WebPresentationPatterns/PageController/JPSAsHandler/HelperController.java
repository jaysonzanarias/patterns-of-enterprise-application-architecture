package WebPresentationPatterns.PageController.JPSAsHandler;

public class HelperController {
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected void forward(String target, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(target);
            if (dispatcher == null) {
                response.sendError(response.SC_NO_CONTENT);
            } else {
                dispatcher.forward(request, response);
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        } catch (ServletException e) {
            throw new ApplicationException(e);
        }
    }
}
