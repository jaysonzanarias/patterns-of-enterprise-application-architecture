package ObjectRelationalBehavioralPatterns.UnitOfWork;

import java.io.IOException;

public abstract class UnitOfWorkServlet {
    final protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            UnitOfWork.newCurrent();
            handleGet(request, response);
            UnitOfWork.getCurrent().commit();
        } finally {
            UnitOfWork.setCurrent(null);
        }
    }

    abstract void handleGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException;
}
