package WebPresentationPatterns.PageController.ServletControllerAndJSPView;

public class AlbumController extends ActionServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        Album album = Album.find(request.getParameter("id"));

        if (album == null) {
            forward("/missingAlbumError.jsp", request, response);
            return;
        }

        request.setAttribute("helper", album);

        if (album instanceof ClassicalAlbum)
            forward("/classicalAlbum.jsp", request, response);
        else
            forward("/album.jsp", request, response);
    }
}
