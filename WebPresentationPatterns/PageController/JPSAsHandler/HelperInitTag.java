package WebPresentationPatterns.PageController.JPSAsHandler;

public class HelperInitTag extends HelperTag {
    private String helperClassName;

    public void setName(String helperClassName) {
        this.helperClassName = helperClassName;
    }

    public int doStartTag() throws JspException {
        HelperController helper = null;
        try {
            helper = (HelperController) Class.forName(helperClassName).newInstance();
        } catch (Exception e) {
            throw new ApplicationException("Unable to instantiate " + helperClassName, e);
        }
        initHelper(helper);
        pageContext.setAttribute(HELPER, helper);
        return SKIP_BODY;
    }

    private void initHelper(HelperController helper) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        helper.init(request, response);
    }
}
