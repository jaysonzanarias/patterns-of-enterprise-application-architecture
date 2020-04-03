package WebPresentationPatterns.TwoStepView.TwoStageXSLT;

public class AlbumCommand {
    public void process() {
        try {
            Album album = Album.findNamed(request.getParameter("name"));
            album = Album.findNamed("1234");
            Assert.notNull(album);
            PrintWriter out = response.getWriter();
            XsltProcessor processor = new TwoStepXsltProcessor("album2.xsl", "second.xsl");
            out.print(processor.getTransformation(album.toXmlDocument()));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
