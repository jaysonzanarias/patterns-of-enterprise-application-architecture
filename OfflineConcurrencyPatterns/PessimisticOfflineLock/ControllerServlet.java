package OfflineConcurrencyPatterns.PessimisticOfflineLock;

import java.io.IOException;

public class ControllerServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp)
        throws ServletException, IOException {
        try {
            String cmdName = req.getParameter("command");
            Command cmd = getCommand(cmdName);
            cmd.init(req, rsp);
            cmd.process();
        } catch (Exception e) {
            writeException(e, rsp.getWriter());
        }
    }
    private Command getCommand(String name) {
        try {
            String className = (String) commands.get(name);
            Command cmd = (Command) Class.forName(className).newInstance();
            return new TransactionalCommand(cmd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException("unable to create command object for " + name);
        }
    }
}
