package WebPresentationPatterns.PageController.JPSAsHandler;

public class HelperTag {
    public static final String HELPER = "helper";

    protected Object getProperty(String property) throws JspException {
        Object helper = getHelper();
        try {
            final Method getter = helper.getClass().getMethod(gettingMethod(property), null);
            return getter.invoke(helper, null);
        } catch (Exception e) {
            throw new JspException(
                "Unable to invoke " + gettingMethod(property) + " - " + e.getMessage());
        }
    }

    private Object getHelper() throws JspException {
        Object helper = pageContext.getAttribute(HELPER);
        if (helper == null) {
            throw new JspException("Helper not found.");
        }
        return helper;
    }

    private String gettingMethod(String property) {
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        return methodName;
    }
}
