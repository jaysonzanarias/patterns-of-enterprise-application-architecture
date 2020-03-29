package WebPresentationPatterns.FrontController;

public class ArtistCommand extends FrontCommand {
    public void process() throws ServletException, IOException {
        Artist artist = Artist.findNamed(request.getParameter("name"));
        request.setAttribute("helper", new ArtistHelper(artist));
        forward("/artist.jsp");
    }
}
