package WebPresentationPatterns.PageController.JPSAsHandler;

public class AlbumConHelper extends HelperController {
    public void init(HttpServletRequest request, HttpServletResponse response) {
        super.init(request, response);
        if (getAlbum() == null) {
            forward("missingAlbumError.jsp", request, response);
        }
        if (getAlbum() instanceof ClassicalAlbum) {
            request.setAttribute("helper", getAlbum());
            forward("/classicalAlbum.jsp", request, response);
        }
    }
}
