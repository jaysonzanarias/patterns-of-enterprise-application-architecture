package WebPresentationPatterns.FrontController;

import java.io.IOException;

public class UnknownCommand extends FrontCommand {
    public void process() throws ServletException, IOException {
        forward("/unknown.jsp");
    }
}
