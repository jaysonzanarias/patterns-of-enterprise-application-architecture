package WebPresentationPatterns.PageController.JPSAsHandler;

public class HelperGetTag extends HelperTag {
    private String propertyName;

    public void setProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(getProperty(propertyName));
        } catch (IOException e) {
            throw new JspException("unable to print to writer");
        }
        return SKIP_BODY;
    }
}
